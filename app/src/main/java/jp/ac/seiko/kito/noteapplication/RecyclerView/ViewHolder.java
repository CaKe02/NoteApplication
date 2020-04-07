package jp.ac.seiko.kito.noteapplication.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import jp.ac.seiko.kito.noteapplication.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextViewTitle;
    public LinearLayout mLinearLayoutTitle;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextViewTitle = itemView.findViewById(R.id.textView_title);
        mLinearLayoutTitle = itemView.findViewById(R.id.linearLayout_title);
    }
}
