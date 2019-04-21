package education.mahmoud.quranyapp.data_layer.remote;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import education.mahmoud.quranyapp.Util.Constants;
import education.mahmoud.quranyapp.data_layer.remote.model.Feedback;
import education.mahmoud.quranyapp.data_layer.remote.model.User;

/**
 * Created by Mahmoud on 10/22/2018.
 */
public class FirebaseDataSource {

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference userRef;
    private DatabaseReference reviews;

    private FirebaseAuth auth;

    public FirebaseDataSource() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        userRef = firebaseDatabase.getReference(Constants.USERS_KEY);
        reviews = firebaseDatabase.getReference(Constants.REVIWES_KEY);
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public String getCurrentUserUUID() {
        return auth.getCurrentUser().getUid();
    }

    public void addUser(User user) {
        if (user.getKey() == null) {
            String key = userRef.push().getKey();
            user.setKey(key);
        }
        userRef.child(user.getKey()).setValue(user);
    }

    public void updateUser(User user) {
        userRef.child(user.getKey()).setValue(user);
    }

    public DatabaseReference getUsers() {
        return userRef.getRef();
    }


    // feedback
    public void addFeedback(Feedback feedback){
        reviews.push().setValue(feedback);
    }

    public DatabaseReference getReviews (){
        return reviews;
    }



}
