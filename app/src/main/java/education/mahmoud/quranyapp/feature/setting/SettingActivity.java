package education.mahmoud.quranyapp.feature.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.data_layer.Repository;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG = "SettingActivity";
    @BindView(R.id.cbNightMode)
    CheckBox cbNightMode;

    Repository repository;
    @BindView(R.id.spColorReqularMode)
    Spinner spColorReqularMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        List<String> colorSet = new ArrayList<>(Arrays.asList(getString(R.string.green), getString(R.string.yellow), getString(R.string.white)));


        repository = Repository.getInstance(getApplication());
        cbNightMode.setChecked(repository.getNightModeState());
        cbNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean state) {
                Log.d(TAG, "onCheckedChanged: " + state);
                repository.setNightModeState(state);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, colorSet);
        spColorReqularMode.setAdapter(adapter);


        spColorReqularMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemSelected: " + i + ":: "+ l );
                int pos  = spColorReqularMode.getSelectedItemPosition();
                Log.d(TAG, "onItemSelected: pos " + pos);
                repository.setBackColorState(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
