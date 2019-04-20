package education.mahmoud.quranyapp.data_layer.remote.model;

import com.google.gson.annotations.SerializedName;

public class User {

    private String name ;
    private String email ;
    private int score ;
        private int n_ayahs;

    public User() {
    }

    public User(String name, String email, int score, int n_ayahs) {
        this.name = name;
        this.email = email;
        this.score = score;
        this.n_ayahs = n_ayahs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalNumAyahs() {
        return n_ayahs;
    }

    public void setTotalNumAyahs(int n_ayahs) {
        this.n_ayahs = n_ayahs;
    }
}