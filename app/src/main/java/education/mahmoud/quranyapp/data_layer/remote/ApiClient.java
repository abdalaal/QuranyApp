package education.mahmoud.quranyapp.data_layer.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String quranUrl = "http://api.alquran.cloud/v1/";
    private static final String userUrl = "https://quranyapp.000webhostapp.com/";

    private static Retrofit retrofitQuran = null;
    private static Retrofit retrofitUser = null;

   static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getRetroQuran() {


        if (retrofitQuran == null) {
            retrofitQuran = new Retrofit.Builder().baseUrl(quranUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofitQuran;
    }

    public static Retrofit getRetroUsers() {
        if (retrofitUser == null) {
            retrofitUser = new Retrofit.Builder()
                    .baseUrl(userUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofitUser;
    }

}
