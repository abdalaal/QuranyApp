package education.mahmoud.quranyapp.feature.bookmark_fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.Util.Constants;
import education.mahmoud.quranyapp.Util.Data;
import education.mahmoud.quranyapp.data_layer.Repository;
import education.mahmoud.quranyapp.data_layer.local.room.BookmarkItem;
import education.mahmoud.quranyapp.feature.show_sura_ayas.ShowSuarhAyas;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment {

    Repository repository;
    BookmarkAdapter bookmarkAdapter;
    @BindView(R.id.rvBookmark)
    RecyclerView rvBookmark;
    Unbinder unbinder;


    private static final String TAG = "BookmarkFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        unbinder = ButterKnife.bind(this, view);
        repository = Repository.getInstance(getActivity().getApplication());
        initRv();
        retriveBookmarks();

        return view;
    }

    private void initRv() {

        bookmarkAdapter = new BookmarkAdapter();
        LinearLayoutManager manager =new LinearLayoutManager(getContext());
        rvBookmark.setAdapter(bookmarkAdapter);
        rvBookmark.setLayoutManager(manager);

        bookmarkAdapter.setIoBookmark(new BookmarkAdapter.IOBookmark() {
            @Override
            public void onBookmarkClick(BookmarkItem item) {
                Intent openAcivity = new Intent(getContext(), ShowSuarhAyas.class);
                int index = getIndexOfString(item.getSuraName());
                Log.d(TAG, "onBookmarkClick: " + index);
                openAcivity.putExtra(Constants.SURAH_INDEX, index);
                openAcivity.putExtra(Constants.LAST_INDEX_Scroll, item.getScrollIndex());
                startActivity(openAcivity);
            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){
            if (bookmarkAdapter != null && repository != null){
                bookmarkAdapter.setBookmarkItemList(repository.getBookmarks());
            }
        }
    }

    private int getIndexOfString(String suraName) {
        List<String> list = new ArrayList<>(Arrays.asList(Data.SURA_NAMES));
        return list.indexOf(suraName);
    }

    private void retriveBookmarks() {
        bookmarkAdapter.setBookmarkItemList(repository.getBookmarks());
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
