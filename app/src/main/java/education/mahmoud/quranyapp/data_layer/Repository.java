package education.mahmoud.quranyapp.data_layer;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import education.mahmoud.quranyapp.Util.Constants;
import education.mahmoud.quranyapp.data_layer.local.LocalShared;
import education.mahmoud.quranyapp.data_layer.local.room.AyahItem;
import education.mahmoud.quranyapp.data_layer.local.room.BookmarkItem;
import education.mahmoud.quranyapp.data_layer.local.room.QuranDB;
import education.mahmoud.quranyapp.data_layer.local.room.SuraItem;
import education.mahmoud.quranyapp.data_layer.remote.Remote;
import education.mahmoud.quranyapp.data_layer.remote.model.Feedback;
import education.mahmoud.quranyapp.data_layer.remote.model.Tafseer;
import education.mahmoud.quranyapp.data_layer.remote.model.User;
import retrofit2.Call;

public class Repository {

    private static LocalShared localShared;
    private static Repository instance;
    private static QuranDB quranDB;
    private static Remote remote;

    private Repository() {
    }

    public static Repository getInstance(Application context) {
        if (instance == null) {
            instance = new Repository();
            localShared = new LocalShared(context);
            quranDB = QuranDB.getInstance(context);
            remote = new Remote();
        }
        return instance;
    }


    // shared
    public void addLastSura(int index) {
        localShared.addLastSura(index);
    }

    public void addLastSuraWithScroll(int index) {
        localShared.addLastSuraWithScroll(index);
    }

    public int getLastSura() {
        return localShared.getLastSura();
    }

    public int getLastSuraWithScroll() {
        return localShared.getLastSuraWithScroll();
    }

    public boolean getPermissionState() {
        return localShared.getPermissionState();
    }

    public void setPermissionState(boolean state) {
        localShared.setPermissionState(state);
    }

    public boolean getNightModeState() {
        return localShared.getNightModeState();
    }

    public void setNightModeState(boolean state) {
        localShared.setNightModeState(state);
    }

    public int getBackColorState() {
        return localShared.getBackColorState();
    }

    public void setBackColorState(int color) {
        localShared.setBackColorState(color);
    }


    public void setScore(long score) {
        localShared.setScore(score);
    }

    public long getScore() {
        return localShared.getScore();
    }


    // suarh db operation
    public void addSurah(SuraItem suraItem) {
        quranDB.surahDAO().addSurah(suraItem);
    }

    public List<String> getSurasNames() {
        return quranDB.surahDAO().getAllSurahNames();
    }

    public SuraItem getSuraByName(String name) {
        return quranDB.surahDAO().getSurahByName(name);
    }


    // ayah db operation
    public void addAyah(AyahItem item) {
        quranDB.ayahDAO().addAyah(item);
    }

    public int getTotlaAyahs() {
        return quranDB.ayahDAO().getAyahCount();
    }

    public List<AyahItem> getAyahsOfSura(int index) {
        return quranDB.ayahDAO().getAllAyahOfSurahIndex(index);
    }

    public List<AyahItem> getAyahSInRange(int start, int end) {
        return quranDB.ayahDAO().getAyahSInRange(start, end);
    }

    public List<AyahItem> getAyahByAyahText(String text) {
        return quranDB.ayahDAO().getAyahByAyahText(text);
    }

    public List<Integer> getAyahNumberNotAudioDownloaded(){
        return quranDB.ayahDAO().getAyahNumberNotAudioDownloaded();

    }
    public AyahItem getAyahByInSurahIndex(int index, int ayahIndex) {
        return quranDB.ayahDAO().getAyahByInSurahIndex(index, ayahIndex);
    }

    public AyahItem getAyahByIndex(int index) {
        return quranDB.ayahDAO().getAyahByIndex(index);
    }

    public void updateAyahItem(AyahItem item) {
        quranDB.ayahDAO().updateAyah(item);
    }

    public int getLastDownloadedChapter() {
        return quranDB.ayahDAO().getLastChapter();
    }

    public int getLastDownloadedAyahAudio() {
        return quranDB.ayahDAO().getLastDownloadedAyahAudio();
    }


    // bookmark
    public List<BookmarkItem> getBookmarks() {
        return quranDB.bookmarkDao().getBookmarks();
    }

    public void addBookmark(BookmarkItem item) {
        quranDB.bookmarkDao().addBookmark(item);
    }

    public void deleteBookmark(BookmarkItem item) {
        quranDB.bookmarkDao().delteBookmark(item);
    }


    // tafseer
    public Call<Tafseer> getChapterTafser(int id) {
        return remote.getChapterTafser(id);
    }


    // remote data

    public FirebaseAuth getAuth() {
        return remote.getAuth();
    }

    public String getCurrentUserUUID() {
        return remote.getCurrentUserUUID();
    }

    public void addUser(User user) {
        remote.addUser(user);
    }

    public void updateUser(User user) {
        remote.updateUser(user);
    }

    public DatabaseReference getUsers() {
        return remote.getUsers();
    }

    public void addFeedback(Feedback feedback) {
        remote.addFeedback(feedback);
    }

    public String getUserName() {
        return localShared.getUserName();
    }

    public void setUserName(String userName) {
       localShared.setUserName(userName);
    }

    public void setUserUUID(String uuid) {
        localShared.setUserUUID(uuid);
    }



    

   /* public Call<String> getUsers() {
        return remote.getUsers();
    }
    public Call<String> signUp(User user) {
       return remote.signUp(user);
    }
    public Call<String> signUp(String name , String mail ,long score, int n_ayahs) {
       return remote.signUp(name, mail, score, n_ayahs);
    }

    public Call<Void> sendFeedback(String pros, String cons, String suggs) {
        return remote.sendFeedback(pros, cons, suggs);
    }

*/


}
