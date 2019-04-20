package education.mahmoud.quranyapp.feature.scores;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.Util.Util;
import education.mahmoud.quranyapp.data_layer.Repository;

public class ScoreActivity extends AppCompatActivity {

    @BindView(R.id.tvTotalScore)
    TextView tvTotalScore;
    @BindView(R.id.btnShowScoreBoard)
    Button btnShowScoreBoard;
    @BindView(R.id.rvScoreboard)
    RecyclerView rvScoreboard;
    @BindView(R.id.lnScore)
    LinearLayout lnScore;

    Repository repository ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);

        repository = Repository.getInstance(getApplication());

        long score = repository.getScore();
        setScore(score);

        //// TODO: 4/18/2019 add recycler for scoreboard
    }

    private void setScore(long score) {
        tvTotalScore.setText(getString(R.string.score_str ,score, Util.getArabicStrOfNum(score)));
    }


}
