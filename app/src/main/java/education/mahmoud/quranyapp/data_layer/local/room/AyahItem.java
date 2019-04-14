package education.mahmoud.quranyapp.data_layer.local.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ayahs")
public class AyahItem {

    @PrimaryKey
    private int ayahIndex;
    private int surahIndex;
    private int pageNum;
    private int juz ;
    private int ayahInSurahIndex;
    private String text;
    private String textClean;
    private String tafseer;
    private String audioPath;


    public AyahItem(int ayahIndex, int surahIndex, int pageNum, int juz, int ayahInSurahIndex, String text, String textClean, String tafseer, String audioPath) {
        this.ayahIndex = ayahIndex;
        this.surahIndex = surahIndex;
        this.pageNum = pageNum;
        this.juz = juz;
        this.ayahInSurahIndex = ayahInSurahIndex;
        this.text = text;
        this.textClean = textClean;
        this.tafseer = tafseer;
        this.audioPath = audioPath;
    }

    @Ignore
    public AyahItem() {
    }

    @Ignore
    public AyahItem(int ayahIndex, int surahIndex, int ayahInSurahIndex, String text, String textClean) {
        this.ayahIndex = ayahIndex;
        this.surahIndex = surahIndex;
        this.ayahInSurahIndex = ayahInSurahIndex;
        this.text = text;
        this.textClean = textClean;
    }

    public int getAyahIndex() {
        return ayahIndex;
    }

    public void setAyahIndex(int ayahIndex) {
        this.ayahIndex = ayahIndex;
    }

    public int getSurahIndex() {
        return surahIndex;
    }

    public void setSurahIndex(int surahIndex) {
        this.surahIndex = surahIndex;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAyahInSurahIndex() {
        return ayahInSurahIndex;
    }

    public void setAyahInSurahIndex(int ayahInSurahIndex) {
        this.ayahInSurahIndex = ayahInSurahIndex;
    }

    public String getTextClean() {
        return textClean;
    }

    public void setTextClean(String textClean) {
        this.textClean = textClean;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getJuz() {
        return juz;
    }

    public void setJuz(int juz) {
        this.juz = juz;
    }

    public String getTafseer() {
        return tafseer;
    }

    public void setTafseer(String tafseer) {
        this.tafseer = tafseer;
    }
}
