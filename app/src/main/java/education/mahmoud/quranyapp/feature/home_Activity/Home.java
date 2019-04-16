package education.mahmoud.quranyapp.feature.home_Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.tjeannin.apprate.AppRate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.Util.Constants;
import education.mahmoud.quranyapp.Util.Util;
import education.mahmoud.quranyapp.data_layer.Repository;
import education.mahmoud.quranyapp.data_layer.local.room.AyahItem;
import education.mahmoud.quranyapp.data_layer.local.room.SuraItem;
import education.mahmoud.quranyapp.feature.ayahs_search.ShowSearchResults;
import education.mahmoud.quranyapp.feature.bookmark_fragment.BookmarkFragment;
import education.mahmoud.quranyapp.feature.listening_activity.ListenFragment;
import education.mahmoud.quranyapp.feature.setting.SettingActivity;
import education.mahmoud.quranyapp.feature.show_sura_ayas.ShowSuarhAyas;
import education.mahmoud.quranyapp.feature.show_sura_list.GoToSurah;
import education.mahmoud.quranyapp.feature.show_sura_list.ShowSuar;
import education.mahmoud.quranyapp.feature.show_sura_list.SuraListFragment;
import education.mahmoud.quranyapp.feature.test_quran.TestFragment;
import education.mahmoud.quranyapp.model.Aya;
import education.mahmoud.quranyapp.model.Sura;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class Home extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.containerHome)
    ViewPager containerHome;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;

    private ViewPager mViewPager;
    private List<Fragment> fragList;
    private List<String> fragTitles;
    private FragmentAdapter adapter;
    private Repository repository;
    private int RC_STORAGE = 1002;
    private boolean isPermissionAllowed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        repository = Repository.getInstance(getApplication());

        if (repository.getTotlaAyahs() == 0)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    fillDBfromJson();
                }
            }).start();

        new AppRate(this).setMinLaunchesUntilPrompt(5)
                .init();

        setSupportActionBar(toolbar);
        setupFragList();
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragList, fragTitles);
        setupPager();
        Stetho.initializeWithDefaults(getApplication());

    }

    public void acquirePermission() {
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

    public boolean isPermissionAllowed() {
        return isPermissionAllowed;
    }

    /**
     * retrive data from json file into room database
     */
    private void fillDBfromJson() {
        Sura[] suralList = Util.getQuran(this).getSurahs();
        Sura[] suralListClean = Util.getQuranClean(this).getSurahs();
        int ayahIndex = 1, surahIndex = 1;
        Sura sura;
        Aya[] cleanAyahs;
        for (int i = 0; i < suralList.length; i++) {
            sura = suralList[i];
            // here ayahIndex represent first ayahIndex in a sura
            // add sura to db (index, startayahindex , n of ayahs)
            repository.addSurah(new SuraItem(surahIndex, ayahIndex, suralList[i].getAyahs().length, sura.getName()));

            // add ayahs of this sura
            // aya (ayah index -global index- , surah index , order inside sura , text , clean text -text withot symbol- )
            cleanAyahs = suralListClean[i].getAyahs(); // hold ayahs of clean sura
            for (int j = 0; j < sura.getAyahs().length; j++) {
                repository.addAyah(new AyahItem(ayahIndex, surahIndex,
                        Integer.parseInt(sura.getAyahs()[j].getNum()),
                        sura.getAyahs()[j].getText(), cleanAyahs[j].getText()));
                ++ayahIndex;// update suraIndex
            }
            surahIndex++; // update suraIndex
        }

    }

    /**
     * create fragments and their titles
     */
    private void setupFragList() {
        fragList = new ArrayList<>();
        fragTitles = new ArrayList<>();

        SuraListFragment fragment = new SuraListFragment();
        addFragemnt(fragment, getString(R.string.read));

        ListenFragment listenFragment = new ListenFragment();
        addFragemnt(listenFragment, getString(R.string.listen));


        TestFragment testFragment = new TestFragment();
        addFragemnt(testFragment, getString(R.string.test));

        BookmarkFragment bookmarkFragment = new BookmarkFragment();
        addFragemnt(bookmarkFragment, getString(R.string.bookmark));

    }

    private void addFragemnt(Fragment fragment, String titile) {
        fragList.add(fragment);
        fragTitles.add(titile);
    }

    /**
     * link view pager with adapter
     */
    private void setupPager() {
        containerHome.setAdapter(adapter);
        tabs.setupWithViewPager(containerHome);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionJump:
                openGoToSura();
                break;
            case R.id.actionSearch:
                openSearch();
                break;
            case R.id.actionSetting:
                openSetting();
                break;
            case R.id.actionGoToLastRead:
                gotoLastRead();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void openSearch() {
        Intent openAcivity = new Intent(this, ShowSearchResults.class);
        startActivity(openAcivity);
    }

    private void gotoLastRead() {
        int index = Repository.getInstance(getApplication()).getLastSura();
        int scroll = Repository.getInstance(getApplication()).getLastSuraWithScroll();
        if (index == -1) {
            Toast.makeText(this, "You Have no saved recitation", Toast.LENGTH_SHORT).show();
            return;
        }
        gotoSuraa(index, scroll);
    }


    private void gotoSuraa(int index, int scroll) {
        Intent openAcivity = new Intent(this, ShowSuarhAyas.class);
        openAcivity.putExtra(Constants.SURAH_INDEX, index);
        openAcivity.putExtra(Constants.LAST_INDEX_Scroll, scroll);
        startActivity(openAcivity);
    }

    private void openGoToSura() {
        FragmentTransaction a = getSupportFragmentManager().beginTransaction();
        GoToSurah goToSurah = new GoToSurah();
        goToSurah.show(a, null);
    }

    private void openSetting() {
        Intent openAcivity = new Intent(this, SettingActivity.class);
        startActivity(openAcivity);
    }
}
