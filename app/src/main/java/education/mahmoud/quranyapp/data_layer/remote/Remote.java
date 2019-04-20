package education.mahmoud.quranyapp.data_layer.remote;

import java.util.Calendar;
import java.util.List;

import education.mahmoud.quranyapp.data_layer.remote.model.Tafseer;
import education.mahmoud.quranyapp.data_layer.remote.model.User;
import education.mahmoud.quranyapp.data_layer.remote.services.TafseerService;
import education.mahmoud.quranyapp.data_layer.remote.services.UserServices;
import retrofit2.Call;

public class Remote {

    private TafseerService tafseerService ;
    private UserServices userServices ;

    public Remote() {
        tafseerService = ApiClient.getRetroQuran().create(TafseerService.class);
        userServices = ApiClient.getRetroUsers().create(UserServices.class);
    }


    // Tafseer
    public Call<Tafseer> getChapterTafser(int id){
        return tafseerService.getTafseer(id);
    }



    // Users

    public Call<String> signUp(User user) {
        return userServices.signUp(user);
    }

    public Call<String> signUp(String name , String mail ,long score, int n_ayahs) {
        return userServices.signUp(name ,mail ,score, n_ayahs);
    }

/*
    public Call<List<User>> getUsers() {
        return userServices.getUsers();
    }*/

    public Call<String> getUsers() {
        return userServices.getUsers();
    }

    public Call<Void> sendFeedback( String pros ,  String cons ,  String suggs ) {
        return userServices.sendFeedback(pros, cons , suggs);
    }


}
