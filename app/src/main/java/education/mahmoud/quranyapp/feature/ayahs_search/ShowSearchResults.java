package education.mahmoud.quranyapp.feature.ayahs_search;

import android.Manifest;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.balsikandar.crashreporter.utils.CrashReporterException;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.Util.Util;
import education.mahmoud.quranyapp.data_layer.Repository;
import education.mahmoud.quranyapp.data_layer.local.room.AyahItem;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ShowSearchResults extends AppCompatActivity implements OnDownloadListener {

    private static final String TAG = "ShowSearchResults";
    private static final int RC_STORAGE = 10002 ;
    @BindView(R.id.edSearch)
    TextInputEditText edSearch;
    @BindView(R.id.rvSearch)
    RecyclerView rvSearch;
    @BindView(R.id.tvNotFound)
    TextView tvNotFound;
    @BindView(R.id.tvSearchCount)
    TextView tvSearchCount;
    SearchResultsAdapter adapter;
    private Repository repository;
    int index;
    String url = "http://cdn.alquran.cloud/media/audio/ayah/ar.alafasy/";


    private boolean isPermissionAllowed ;
    private List<AyahItem> ayahItems;
    private String ayah;
    private int itemPos; // hold pos of item in adapter used for updating item

    private void foundState() {
        rvSearch.setVisibility(View.VISIBLE);
        tvNotFound.setVisibility(View.GONE);
    }

    private void notFoundState() {
        rvSearch.setVisibility(View.GONE);
        tvNotFound.setVisibility(View.VISIBLE);
    }

    String downURL, path, filename;
    private MediaPlayer mediaPlayer;

    private void acquirePermission() {
        String[] perms = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        EasyPermissions.requestPermissions(new PermissionRequest.Builder(this, RC_STORAGE, perms).build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RC_STORAGE && grantResults[0] == PERMISSION_GRANTED) {
            isPermissionAllowed = true;
            repository.setPermissionState(true);
        } else {
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_search_results);
        ButterKnife.bind(this);

        repository = Repository.getInstance(getApplication());
        isPermissionAllowed = repository.getPermissionState();
        initRv();

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ayah = editable.toString();
                if (ayah.length() > 0) {
                    ayahItems = repository.getAyahByAyahText(ayah);
                    Log.d(TAG, "afterTextChanged: " + ayahItems.size());

                    int count = ayahItems.size(); // n of results
                    tvSearchCount.setText(getString(R.string.times, count));
                    if (count > 0) {
                        adapter.setAyahItemList(ayahItems, ayah);
                        foundState();
                    } else {
                        notFoundState();
                    }
                }

            }
        });

    }

    private void initRv() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvSearch.setLayoutManager(manager);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "kfgqpc_naskh.ttf");
        adapter = new SearchResultsAdapter(typeface);
        rvSearch.setAdapter(adapter);


        adapter.setiOnDownload(new SearchResultsAdapter.IOnDownload() {
            @Override
            public void onDownloadClick(AyahItem item , int pos ) {
                showMessage(getString(R.string.downloading));
                downloadAudio(item);
                itemPos = pos ;
            }
        });

        adapter.setiOnPlay(new SearchResultsAdapter.IOnPlay() {
            @Override
            public void onPlayClick(AyahItem item) {
                playAudio(item);
            }

        });

    }

    private void playAudio(AyahItem item) {
        Log.d(TAG, "playAudio: ");
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(item.getAudioPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
            } else {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(item.getAudioPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
            }

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer1) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("file " + item.getAyahIndex() + " is correupt ");
            showMessage("Problem with file , contact us ,  " + e.getMessage());
        }

    }

    private void downloadAudio(AyahItem item) {
        if (isPermissionAllowed) {
            // compute index
            index = item.getAyahIndex();
            // form  URL
            downURL = url + index;
            // form path
            path = Util.getDirectoryPath(); // get folder path
            // form file name
            filename = index + ".mp3";
            Log.d(TAG, "downloadAudio:  file name " + filename);
            //start downloading
            PRDownloader.download(downURL, path, filename).build().start(this);
        } else {
            acquirePermission();
        }

    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownloadComplete() {
        showMessage("Finish");
        Log.d(TAG, "onDownloadComplete: ");
        // store storage path in db to use in media player
        AyahItem ayahItem = repository.getAyahByIndex(index); // first get ayah to edit it with storage path
       /* // get index to usued in update in adapter
       // int itemIndex = adapter.getItemIndex(ayahItem);
        int itemIndex = ayahItems.indexOf(ayahItem);
        Log.d(TAG, "onDownloadComplete: " + itemIndex);
*/

        String storagePath = path + "/" + filename;
        ayahItem.setAudioPath(storagePath); // set path
        repository.updateAyahItem(ayahItem);
        adapter.updateItem(ayahItem , itemPos);

        playAudio(ayahItem);

    }

    @Override
    public void onError(Error error) {
        if (error.isConnectionError()){
            showMessage(getString(R.string.error_net));
        }else if (error.isServerError()){
            showMessage("Error with server , plz try later ");
        }
        else{
            showMessage("" + error.toString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer != null){
            mediaPlayer.release();
        }


    }
}
