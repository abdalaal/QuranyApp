package education.mahmoud.quranyapp.feature.show_sura_list;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.Util.Constants;
import education.mahmoud.quranyapp.Util.Data;
import education.mahmoud.quranyapp.data_layer.Repository;
import education.mahmoud.quranyapp.feature.show_sura_ayas.ShowSuarhAyas;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuraListFragment extends Fragment {

    @BindView(R.id.rvSura)
    RecyclerView rvSura;
    Unbinder unbinder;

    Repository repository ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sura_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        repository = Repository.getInstance(getActivity().getApplication());

        initRV();
        return view;
    }

    private void initRV() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "kfgqpc_naskh.ttf");
        SuraAdapter suraAdapter = new SuraAdapter(typeface);
        rvSura.setLayoutManager(linearLayoutManager);
        rvSura.setAdapter(suraAdapter);
        suraAdapter.setStringList(Arrays.asList(Data.SURA_NAMES));

        suraAdapter.setSuraListner(new SuraAdapter.SuraListner() {
            @Override
            public void onSura(int pos) {
                gotoSuraa(pos, 0);
            }
        });

    }

    private void gotoSuraa(int pos, int i) {
        Intent openAcivity = new Intent(getContext(), ShowSuarhAyas.class);
        openAcivity.putExtra(Constants.SURAH_INDEX, pos);
        openAcivity.putExtra(Constants.LAST_INDEX_Scroll, 0);
        startActivity(openAcivity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
