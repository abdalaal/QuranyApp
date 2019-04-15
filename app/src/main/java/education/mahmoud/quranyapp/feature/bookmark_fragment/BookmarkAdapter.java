package education.mahmoud.quranyapp.feature.bookmark_fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.data_layer.local.room.BookmarkItem;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.Holder> {



    private List<BookmarkItem> list;

    public BookmarkAdapter() {
        list = new ArrayList<>();
    }

    public void addBookmark(BookmarkItem item) {
        list.add(item);
        notifyItemInserted(list.size() - 1);
        notifyItemRangeChanged(list.size() - 1, list.size());
    }


    public void setBookmarkItemList(List<BookmarkItem> newList) {
        list = new ArrayList<>(newList);
        notifyDataSetChanged();
    }


    public List<BookmarkItem> getList() {
        return list;
    }

    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bookmark_item, viewGroup, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        BookmarkItem item = list.get(i);
        holder.tvIndexBookmark.setText(""+item.getScrollIndex());
        holder.tvSuraNameBookmark.setText(item.getSuraName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvIndexBookmark)
        TextView tvIndexBookmark;
        @BindView(R.id.tvSuraNameBookmark)
        TextView tvSuraNameBookmark;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}