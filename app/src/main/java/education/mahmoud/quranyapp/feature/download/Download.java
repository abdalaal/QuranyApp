package education.mahmoud.quranyapp.feature.download;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.data_layer.Repository;
import education.mahmoud.quranyapp.data_layer.local.room.AyahItem;
import education.mahmoud.quranyapp.data_layer.remote.model.Ayah;
import education.mahmoud.quranyapp.data_layer.remote.model.Tafseer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Download extends AppCompatActivity {

    @BindView(R.id.btnDownloadTafseer)
    Button btnDownloadTafseer;
    @BindView(R.id.spinListening)
    SpinKitView spinListening;
    @BindView(R.id.tvDownStatePercentage)
    TextView tvDownStatePercentage;
    @BindView(R.id.lnDown)
    LinearLayout lnDown;

    Repository repository;
    private int currentChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);

        repository = Repository.getInstance(getApplication());

        currentChapter = repository.getLastDownloadedChapter();
        Log.d(TAG, "onCreate: " + currentChapter);
        if (currentChapter != 0){
            // to make sure if download non-complete juz(chapter)
            currentChapter -= 1 ;
        }

    }

    @OnClick(R.id.btnDownloadTafseer)
    public void onViewClicked() {
        downState();
        loadTafseer();
    }

    private void downState() {
        lnDown.setVisibility(View.VISIBLE);
    }


    private void loadTafseer() {
        currentChapter++;
        setUI();
        if (currentChapter <= 114)
            loadChapter(currentChapter);
    }

    private void setUI() {
        tvDownStatePercentage.setText(getString(R.string.downState, currentChapter, 114));
    }

    private void loadChapter(int currentChapter) {

        repository.getChapterTafser(currentChapter).enqueue(new Callback<Tafseer>() {
            @Override
            public void onResponse(Call<Tafseer> call, Response<Tafseer> response) {
                Tafseer tafseer = response.body();
                List<Ayah> ayahs = tafseer.getData().getAyahs();
                for (Ayah ayah : ayahs) {
                    AyahItem ayahItem = repository.getAyahByIndex(ayah.getNumber());
                    ayahItem.setJuz(ayah.getJuz());
                    ayahItem.setPageNum(ayah.getPage());
                    ayahItem.setTafseer(ayah.getText());
                    repository.updateAyahItem(ayahItem);
                 //   Log.d(TAG, "onResponse: updated " + ayah.getNumber());
                }
                loadTafseer();
            }

            @Override
            public void onFailure(Call<Tafseer> call, Throwable t) {
                showMessage("Error , try again");
                defaultState();
            }
        });


    }

    private void defaultState() {
        lnDown.setVisibility(View.GONE);
    }

    private static final String TAG = "Main2Activity";

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//         Log.d(TAG, "showMessage: " + message);
    }
}
