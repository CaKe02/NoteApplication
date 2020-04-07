package jp.ac.seiko.kito.noteapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import jp.ac.seiko.kito.noteapplication.DataBase.NoteDataBaseHelper;
import jp.ac.seiko.kito.noteapplication.R;


public class EditingFragment extends Fragment implements View.OnClickListener {

    private String mTitleText;
    private String mBodyText;

    private Button mButtonDone;
    private Button mButtonCancel;
    private EditText mEditTextTitle;
    private EditText mEditTextBody;


    private final static String KEY_FIGURE = "figure";
    private int mFigure = 0;



    public static EditingFragment newInstance(int figure) {
        EditingFragment fragment = new EditingFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_FIGURE, figure);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mFigure = args.getInt(KEY_FIGURE, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_editing, container, false);
        init(rootView); // 授業ではやらなかったけどこうするといい。
        return rootView;
    }

    private void init(View v) {
        mButtonDone = v.findViewById(R.id.button_done);
        mButtonCancel = v.findViewById(R.id.button_cancel);
        mEditTextTitle = v.findViewById(R.id.editText_title);
        mEditTextBody = v.findViewById(R.id.editText_body);
        mButtonDone.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_done:
                NoteDataBaseHelper noteDB = new NoteDataBaseHelper(getContext());
                mTitleText = mEditTextTitle.getText().toString();
                mBodyText = mEditTextBody.getText().toString();
                noteDB.addProject(mTitleText,mBodyText,"1");

                MainFragment fragmentDone = MainFragment.newInstance(mFigure + 1); // 遷移先
                FragmentTransaction fragmentTransactionDone = getFragmentManager().beginTransaction();
                fragmentTransactionDone.replace(R.id.linearLayout_container, fragmentDone);
                fragmentTransactionDone.addToBackStack(fragmentDone.getClass().getSimpleName());
                fragmentTransactionDone.commit();
                break;
            case R.id.button_cancel:
                MainFragment fragmentCancel = MainFragment.newInstance(mFigure); // 遷移先
                FragmentTransaction fragmentTransactionCancel = getFragmentManager().beginTransaction();
                fragmentTransactionCancel.replace(R.id.linearLayout_container, fragmentCancel);
                fragmentTransactionCancel.addToBackStack(fragmentCancel.getClass().getSimpleName());
                fragmentTransactionCancel.commit();
                break;
        }
    }
}
