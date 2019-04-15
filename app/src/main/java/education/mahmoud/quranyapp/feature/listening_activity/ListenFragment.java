package education.mahmoud.quranyapp.feature.listening_activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.data_layer.Repository;
import education.mahmoud.quranyapp.data_layer.local.room.AyahItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListenFragment extends Fragment {


    String fileSource;
    List<AyahItem> ayahsToListen;
    int actualStart, actualEnd;
    int currentIteration = 0, endIteration;
    private Repository repository;
    private ArrayList<AyahItem> ayahsToDownLoad;
    private int ayahsRepeatCount;
    private int ayahsSetCount;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_listen, container, false);
        return view;
    }

    public String getTitle(){
        return getString(R.string.listen);
    }

}
