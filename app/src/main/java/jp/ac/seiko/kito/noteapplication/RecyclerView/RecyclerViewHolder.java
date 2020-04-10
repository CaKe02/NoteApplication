package jp.ac.seiko.kito.noteapplication.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import jp.ac.seiko.kito.noteapplication.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextViewTitle;
    public TextView mTextViewTitleId;
    public Button mButtonDelete;
    public LinearLayout mLinearLayoutTitle;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextViewTitle = itemView.findViewById(R.id.textView_title);
        mTextViewTitleId = itemView.findViewById(R.id.textView_titleId);
        mButtonDelete = itemView.findViewById(R.id.button_delete);
        mLinearLayoutTitle = itemView.findViewById(R.id.linearLayout_title);
    }
}
