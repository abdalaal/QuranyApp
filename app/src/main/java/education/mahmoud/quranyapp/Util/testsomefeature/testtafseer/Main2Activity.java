package education.mahmoud.quranyapp.Util.testsomefeature.testtafseer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.data_layer.Repository;
import education.mahmoud.quranyapp.data_layer.local.room.AyahItem;
import education.mahmoud.quranyapp.data_layer.remote.model.Ayah;
import education.mahmoud.quranyapp.data_layer.remote.model.Tafseer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {

    int currentChapter = 0 ;
    Repository repository ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        repository = Repository.getInstance(getApplication());
        loadTafseer();
    }

    private void loadTafseer() {
        currentChapter ++ ;
        if (currentChapter <= 114 )
           loadChapter(currentChapter);
    }

    private void loadChapter(int currentChapter) {

        repository.getChapterTafser(currentChapter).enqueue(new Callback<Tafseer>() {
            @Override
            public void onResponse(Call<Tafseer> call, Response<Tafseer> response) {
                Tafseer tafseer = response.body();
                List<Ayah> ayahs  = tafseer.getData().getAyahs();
                for(Ayah ayah : ayahs){
                    AyahItem ayahItem = repository.getAyahByIndex(ayah.getNumber());
                    ayahItem.setJuz(ayah.getJuz());
                    ayahItem.setPageNum(ayah.getPage());
                    ayahItem.setTafseer(ayah.getText());
                    repository.updateAyahItem(ayahItem);
                    Log.d(TAG, "onResponse: updated " + ayah.getNumber() );
                }

                loadTafseer();
            }

            @Override
            public void onFailure(Call<Tafseer> call, Throwable t) {
//                Toast.makeText(Main2Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private static final String TAG = "Main2Activity";

     private void showMessage(String message) {
           //  Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//         Log.d(TAG, "showMessage: " + message);
       }
}
