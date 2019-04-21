package education.mahmoud.quranyapp.feature.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import durdinapps.rxfirebase2.RxFirebaseAuth;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.data_layer.Repository;
import education.mahmoud.quranyapp.data_layer.remote.model.User;
import education.mahmoud.quranyapp.feature.scores.ScoreActivity;

public class RegisterActivity extends AppCompatActivity {


    @BindView(R.id.edResgisterName)
    EditText edResgisterName;
    @BindView(R.id.edResgisterEmail)
    EditText edResgisterEmail;
    @BindView(R.id.edResgisterPassword)
    EditText edResgisterPassword;
    @BindView(R.id.btnResgister)
    Button btnResgister;
    @BindView(R.id.tvOpenLogin)
    TextView tvOpenLogin;

    Repository repository;
    String name, email, password;


    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        repository = Repository.getInstance(getApplication());

    }

    @OnClick(R.id.btnResgister)
    public void onBtnResgisterClicked() {
        name = edResgisterName.getText().toString();
        email = edResgisterEmail.getText().toString();
        password = edResgisterPassword.getText().toString();

        if (checkInput(name)) {
            if (checkInput(email)) {
                if (checkInput(password)) {
                    RxFirebaseAuth.createUserWithEmailAndPassword(repository.getAuth(), email, password)
                            .subscribe(logged -> {
                                try {
                                    Log.d(TAG, "onBtnResgisterClicked:  logged " + logged.getUser().getDisplayName());
                                    User user = new User();
                                    user.setName(name);
                                    user.setEmail(email);
                                    user.setKey(logged.getUser().getUid());
                                    // add user name and uuid  in local shared
                                    repository.setUserName(name);
                                    repository.setUserUUID(logged.getUser().getUid());
                                    //add to database online
                                    repository.addUser(user);
                                    // open ScoreActivity
                                    Intent openAcivity = new Intent(RegisterActivity.this, ScoreActivity.class);
                                    startActivity(openAcivity);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }, throwable -> {
                                Log.d(TAG, "onBtnResgisterClicked:  error "+throwable.getMessage());
                            });
                } else {
                    edResgisterPassword.setError(getString(R.string.wrong_pass));
                }

            } else {
                edResgisterEmail.setError(getString(R.string.wrong_email));
            }

        } else {
            edResgisterName.setError(getString(R.string.wrong_name));
        }

    }

    private boolean checkInput(String input) {
        return !TextUtils.isEmpty(input) && input.replaceAll(" ", "").length() != 0;
    }

    @OnClick(R.id.tvOpenLogin)
    public void onTvOpenLoginClicked() {
    }
}
