package jp.ac.seiko.kito.noteapplication.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jp.ac.seiko.kito.noteapplication.R;


public class RVAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<RowDataModel> list;

    private OnItemClickListener mListener;

    public RVAdapter(List<RowDataModel> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_row, parent, false);
        return new RecyclerViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        holder.mTextViewTitle.setText(list.get(position).getTitle());
        holder.mTextViewTitleId.setText(list.get(position).getTitleId());
        holder.mLinearLayoutTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(list.get(position).getTitleId(), 0);
            }
        });
        holder.mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(list.get(position).getTitleId(), 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String id, int isDeleteButton);
    }
}
