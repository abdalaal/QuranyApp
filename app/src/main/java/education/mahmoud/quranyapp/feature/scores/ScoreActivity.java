package education.mahmoud.quranyapp.feature.scores;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.Util.Util;
import education.mahmoud.quranyapp.data_layer.Repository;

public class ScoreActivity extends AppCompatActivity {

    @BindView(R.id.tvTotalScore)
    TextView tvTotalScore;
    @BindView(R.id.btnShowScoreBoard)
    Button btnShowScoreBoard;


    Repository repository;
    @BindView(R.id.tvWelcomeUser)
    TextView tvWelcomeUser;
    @BindView(R.id.loggedLayout)
    LinearLayout loggedLayout;
    @BindView(R.id.btnUploadScore)
    Button btnUploadScore;
    @BindView(R.id.btnOpenLogin)
    Button btnOpenLogin;


    private static final String TAG = "ScoreActivity";
    String name , uuid ;
    long score ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);

        repository = Repository.getInstance(getApplication());

        score = repository.getScore();
        setScore();
        uuid = repository.getCurrentUserUUID();

        name = repository.getUserName();
        // check and fork
        if (name == null) { // not a registered user
            NotUserState();
        } else {
            UserState();
        }


    }

    private void UserState() {
        tvWelcomeUser.setText(getString(R.string.welcome, name));
        btnUploadScore.setVisibility(View.VISIBLE);
        btnOpenLogin.setVisibility(View.GONE);
    }

    private void NotUserState() {
        tvWelcomeUser.setText(R.string.not_req);
        btnUploadScore.setVisibility(View.GONE);
    }

    private void setScore() {
        tvTotalScore.setText(getString(R.string.score_str, score, Util.getArabicStrOfNum(score)));
    }


    @OnClick(R.id.btnUploadScore)
    public void onBtnUploadScoreClicked() {
     /*   RxFirebaseDatabase
                .setValue(repository.getUsers().
                        child(uuid).child("score") ,score)
                .doOnComplete(() ->{
                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                }  );*/
        repository.getUsers().
                child(uuid).child("score").setValue(score).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()){
                    Log.d(TAG, "onComplete: ");
                }
                Log.d(TAG, "onComplete: " + task.toString());
            }
        });
    }

    @OnClick(R.id.btnOpenLogin)
    public void onBtnOpenLoginClicked() {

    }
}
