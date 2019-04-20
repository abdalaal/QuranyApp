package education.mahmoud.quranyapp.feature.feedback_activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.data_layer.Repository;

public class FeedbackActivity extends AppCompatActivity {

    @BindView(R.id.edPositives)
    TextInputEditText edPositives;
    @BindView(R.id.edNegatives)
    TextInputEditText edNegatives;
    @BindView(R.id.edSuggestions)
    TextInputEditText edSuggestions;
    @BindView(R.id.btnSendFeedback)
    Button btnSendFeedback;


    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        repository = Repository.getInstance(getApplication());

    }
}
