package education.mahmoud.quranyapp.data_layer.local.room;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AyahDAO {

    @Insert
    public void addAyah(AyahItem item);

    @Update
    public void updateAyah(AyahItem item);

    @Query("select  * from ayahs where `surahIndex` = :id order by ayahInSurahIndex asc")
    public List<AyahItem> getAllAyahOfSurahIndex(int id);

    @Query("select  * from ayahs where `ayahIndex` between :start and :end order by ayahIndex asc ")
    public List<AyahItem> getAyahSInRange(int start, int end);

    @Query("select  * from ayahs where `ayahIndex` = :id")
    public AyahItem getAyahByIndex(int id);

    @Query("select  * from ayahs where textClean like  '%' || :txt || '%' ")
    public List<AyahItem> getAyahByAyahText(String txt);

    @Query("select ayahIndex from ayahs where audioPath is  null ")
    public List<Integer> getAyahNumberNotAudioDownloaded();

    @Query("select count(*) from ayahs")
    public int getAyahCount();

    @Query("select max(surahIndex) from ayahs where tafseer is not null ")
    public int getLastChapter();

    @Query("select  * from ayahs where `surahIndex` = :index  and ayahInSurahIndex = :ayahIndex")
    AyahItem getAyahByInSurahIndex(int index, int ayahIndex);

    @Query("select max(ayahIndex) from ayahs where audioPath is not null ")
    int getLastDownloadedAyahAudio();
}
