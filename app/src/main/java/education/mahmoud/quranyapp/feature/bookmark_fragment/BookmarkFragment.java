package education.mahmoud.quranyapp.feature.bookmark_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.data_layer.Repository;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment {

    Repository repository;
    BookmarkAdapter bookmarkAdapter;
    @BindView(R.id.rvBookmark)
    RecyclerView rvBookmark;
    Unbinder unbinder;

    public String getTitle() {
        return getString(R.string.bookmark);
    }


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
