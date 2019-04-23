package education.mahmoud.quranyapp.feature.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import durdinapps.rxfirebase2.RxFirebaseAuth;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.Util.Util;
import education.mahmoud.quranyapp.data_layer.Repository;
import education.mahmoud.quranyapp.data_layer.remote.model.User;
import education.mahmoud.quranyapp.feature.home_Activity.Home;
import education.mahmoud.quranyapp.feature.login.Login;

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
    @BindView(R.id.spRegister)
    SpinKitView spRegister;

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

        if (Util.checkInput(name)) {
            if (Util.checkInput(email)) {
                if (Util.checkInput(password)) {
                    StartOperationState();
                    RxFirebaseAuth.createUserWithEmailAndPassword(repository.getAuth(), email, password)
                            .subscribe(logged -> {
                                FinishOperationState();
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
                                    showMessage(getString(R.string.created_Account));
                                    Intent openAcivity = new Intent(RegisterActivity.this, Home.class);
                                    openAcivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(openAcivity);
                                    finish();


                                } catch (Exception e) {
                                    showMessage("Error ");
                                }

                            }, throwable -> {
                                FinishOperationState();
                                Log.d(TAG, "onBtnResgisterClicked:  error " + throwable.getMessage());
                                showMessage("Error");
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

    private void StartOperationState() {
        btnResgister.setVisibility(View.GONE);
        spRegister.setVisibility(View.VISIBLE);
    }

    private void FinishOperationState() {
        btnResgister.setVisibility(View.VISIBLE);
        spRegister.setVisibility(View.GONE);
    }

    @OnClick(R.id.tvOpenLogin)
    public void onTvOpenLoginClicked() {
        Intent openAcivity = new Intent(RegisterActivity.this, Login.class);
        //  openAcivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(openAcivity);
        finish();
    }


    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
