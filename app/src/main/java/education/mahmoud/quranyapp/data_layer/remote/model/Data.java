
package education.mahmoud.quranyapp.data_layer.remote.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    int number;
    String text;
    Edition edition;
    //Surah surah;
    int numberInSurah;
    int juz;
    int manzil;
    int page;
    int ruku;
    int hizbQuarter;
    boolean sajda;


    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    public Data(int number, String text, Edition edition,  int numberInSurah, int juz, int manzil, int page, int ruku, int hizbQuarter, boolean sajda) {
        this.number = number;
        this.text = text;
        this.edition = edition;
     //   this.surah = surah;
        this.numberInSurah = numberInSurah;
        this.juz = juz;
        this.manzil = manzil;
        this.page = page;
        this.ruku = ruku;
        this.hizbQuarter = hizbQuarter;
        this.sajda = sajda;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public int getNumberInSurah() {
        return numberInSurah;
    }

    public void setNumberInSurah(int numberInSurah) {
        this.numberInSurah = numberInSurah;
    }

    public int getJuz() {
        return juz;
    }

    public void setJuz(int juz) {
        this.juz = juz;
    }

    public int getManzil() {
        return manzil;
    }

    public void setManzil(int manzil) {
        this.manzil = manzil;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRuku() {
        return ruku;
    }

    public void setRuku(int ruku) {
        this.ruku = ruku;
    }

    public int getHizbQuarter() {
        return hizbQuarter;
    }

    public void setHizbQuarter(int hizbQuarter) {
        this.hizbQuarter = hizbQuarter;
    }

    public boolean isSajda() {
        return sajda;
    }

    public void setSajda(boolean sajda) {
        this.sajda = sajda;
    }
}
