package education.mahmoud.quranyapp.feature.test_quran;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.data_layer.Repository;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    Repository repository ;
    Activity activity ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_test, container, false);
        activity = getActivity();
        repository = Repository.getInstance(activity.getApplication());


        return view;
    }

    public String getTitle(){
        return getString(R.string.test);
    }

}
