package education.mahmoud.quranyapp.feature.test_quran;

import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import education.mahmoud.quranyapp.R;
import education.mahmoud.quranyapp.data_layer.local.AyahItem;
import education.mahmoud.quranyapp.model.Aya;

public class SaveTestAdapter extends RecyclerView.Adapter<SaveTestAdapter.Holder> {

    private List<AyahItem> list;
    private IOnTestClick iOnTestClick ;


    public SaveTestAdapter() {
        list = new ArrayList<>();
    }


    public void setiOnTestClick(IOnTestClick iOnTestClick) {
        this.iOnTestClick = iOnTestClick;
    }

    public void setAyahItemList(List<AyahItem> newList) {
        list = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public List<AyahItem> getList() {
        return list;
    }

    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.test_item, viewGroup, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int i) {
        final AyahItem item = list.get(i);
        holder.edTextToTest.setText("");
        holder.edTextToTest.setEnabled(true);
        holder.tvAyahNum.setText(""+item.getAyahInSurahIndex());
        holder.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.edTextToTest.setEnabled(false);
                iOnTestClick.onClickTestCheck(item , holder.edTextToTest);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvAyahNum)
        TextView tvAyahNum;
        @BindView(R.id.edTextToTest)
        TextInputEditText edTextToTest;
        @BindView(R.id.btnCheck)
        Button btnCheck;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    interface  IOnTestClick {
        void onClickTestCheck(AyahItem item , TextInputEditText editText);
    }


}