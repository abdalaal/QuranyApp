package education.mahmoud.quranyapp.feature.feedback_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import durdinapps.rxfirebase2.RxFirebaseAuth;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.Util.Util;
import education.mahmoud.quranyapp.data_layer.Repository;
import education.mahmoud.quranyapp.data_layer.remote.model.Feedback;
import education.mahmoud.quranyapp.data_layer.remote.model.User;
import education.mahmoud.quranyapp.feature.register.RegisterActivity;
import education.mahmoud.quranyapp.feature.scores.ScoreActivity;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class FeedbackActivity extends AppCompatActivity {

    private static final String TAG = "FeedbackActivity";

    @BindView(R.id.edPositives)
    TextInputEditText edPositives;
    @BindView(R.id.edNegatives)
    TextInputEditText edNegatives;
    @BindView(R.id.edSuggestions)
    TextInputEditText edSuggestions;
    @BindView(R.id.btnSendFeedback)
    Button btnSendFeedback;

    Repository repository;
    String pros, cons, suggs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        repository = Repository.getInstance(getApplication());

    }

    @OnClick(R.id.btnSendFeedback)
    public void onViewClicked() {

        pros = edPositives.getText().toString();
        cons = edNegatives.getText().toString();
        suggs = edSuggestions.getText().toString();

        if (Util.checkInput(pros)) {
            if (Util.checkInput(cons)) {
                RxFirebaseDatabase.setValue(repository.getReviews().push() , new Feedback(pros, cons, suggs))
                        .doOnError(new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                showMessage("Error, check your Interent Connection");
                            }
                        })
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                blankFields();
                                showMessage(getString(R.string.thanks));
                            }
                        });

            } else {
                edNegatives.setError(getString(R.string.empty));
            }
        } else {
            edPositives.setError(getString(R.string.empty));
        }
    }

    private void blankFields() {
        edPositives.setText(null);
        edNegatives.setText(null);
        edSuggestions.setText(null);
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
