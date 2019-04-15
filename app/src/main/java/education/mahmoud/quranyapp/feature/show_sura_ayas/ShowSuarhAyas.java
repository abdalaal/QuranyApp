package education.mahmoud.quranyapp.feature.show_sura_ayas;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.Util.Constants;
import education.mahmoud.quranyapp.Util.Data;
import education.mahmoud.quranyapp.Util.Util;
import education.mahmoud.quranyapp.data_layer.Repository;
import education.mahmoud.quranyapp.data_layer.local.room.AyahItem;
import education.mahmoud.quranyapp.data_layer.local.room.BookmarkItem;

public class ShowSuarhAyas extends AppCompatActivity {

    @BindView(R.id.tvAyahs)
    TextView tvAyahs;
    @BindView(R.id.tvSuraNameShowAyas)
    TextView tvSuraNameShowAyas;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.lnShowAyahs)
    LinearLayout lnShowAyahs;
    @BindView(R.id.imBookmark)
    ImageView imBookmark;

    private Repository repository;
    int index;
    private static final String TAG = "ShowSuarhAyas";
    int scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_suarh_ayas);
        ButterKnife.bind(this);


        repository = Repository.getInstance(getApplication());
        // get index from intent
        index = getIntent().getIntExtra(Constants.SURAH_INDEX, 0);
        tvSuraNameShowAyas.setText(Data.SURA_NAMES[index]);

        loadSurahFromDb2UI(index);
        // add last sura
        Repository.getInstance(getApplication()).addLastSura(index);
        // check if get from last read option
        scroll = getIntent().getIntExtra(Constants.LAST_INDEX_Scroll, 0);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "kfgqpc_naskh.ttf");
        tvAyahs.setTypeface(typeface);
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  showMessage(" intented scroll " + scroll);
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.smoothScrollTo(0, scroll);
            }
        });

        /*
         *
         * ResourcesCompat.getColor(getResources(), R.color.your_color, null); //without themes
         */
        // check Night Mode
        if (repository.getNightModeState()) {
            tvSuraNameShowAyas.setTextColor(getResources().getColor(R.color.ayas_color_night_mode));
            tvAyahs.setTextColor(getResources().getColor(R.color.ayas_color_night_mode));
            lnShowAyahs.setBackgroundColor(getResources().getColor(R.color.bg_ays_night_mode));
        } else {
            tvSuraNameShowAyas.setTextColor(getResources().getColor(R.color.ayas_color));
            tvAyahs.setTextColor(getResources().getColor(R.color.ayas_color));

            // check user color for background
            int col = repository.getBackColorState();
            switch (col) {
                case Constants.GREEN:
                    lnShowAyahs.setBackgroundColor(getResources().getColor(R.color.bg_green));
                    break;
                case Constants.WHITE:
                    lnShowAyahs.setBackgroundColor(getResources().getColor(R.color.bg_white));
                    break;

                case Constants.YELLOW:
                    lnShowAyahs.setBackgroundColor(getResources().getColor(R.color.bg_yellow));
                    break;
            }


        }
    }

    private void loadSurahFromDb2UI(int index) {
        index++; // index in db start from 1
        StringBuilder builder = new StringBuilder();
        List<AyahItem> ayahs = repository.getAyahsOfSura(index);
        String aya;
        for (AyahItem ayahItem : ayahs) {
            aya = ayahItem.getText();
            builder.append(aya + " (" + ayahItem.getAyahInSurahIndex() + ") ");
        }
        String res = builder.toString();
        tvAyahs.setText(Util.getSpannable(res), TextView.BufferType.SPANNABLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Repository.getInstance(getApplication()).addLastSura(index);
        int lastScroll = scrollView.getScrollY();
        Repository.getInstance(getApplication()).addLastSuraWithScroll(lastScroll);
     }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.imBookmark)
    public void onViewClicked() {
        BookmarkItem bookmarkItem = new BookmarkItem();

        int scroll = scrollView.getScrollY();
        String name = Data.SURA_NAMES[index];
        long miilis = new Date().getTime();

        bookmarkItem.setScrollIndex(scroll);
        bookmarkItem.setSuraName(name);
        bookmarkItem.setTimemills(miilis);

        repository.addBookmark(bookmarkItem);

        showMessage(getString(R.string.saved));
    }
}
