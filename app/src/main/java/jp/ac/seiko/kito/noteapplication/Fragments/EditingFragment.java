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

    private int mTitleId;
    private String mTitleText;
    private String mBodyText;
    private int mIsEditing;

    private Button mButtonDone;
    private Button mButtonCancel;
    private EditText mEditTextTitle;
    private EditText mEditTextBody;

    private final static String KEY_TITLEID = "titleId";
    private final static String KEY_TITLE = "title";
    private final static String KEY_BODY = "body";
    private final static String KEY_ISEDITING = "isEditing";



    public static EditingFragment newInstance(int id, String title, String body, int isEditing) {
        EditingFragment fragment = new EditingFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_TITLEID, id);
        args.putString(KEY_TITLE, title);
        args.putString(KEY_BODY, body);
        args.putInt(KEY_ISEDITING, isEditing);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mTitleId = args.getInt(KEY_TITLEID, 0);
            mTitleText = args.getString(KEY_TITLE);
            mBodyText = args.getString(KEY_BODY);
            mIsEditing = args.getInt(KEY_ISEDITING, 0);
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

        if (mIsEditing == 1) {
            mEditTextTitle.setText(mTitleText);
            mEditTextBody.setText(mBodyText);
        }
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_done:
                NoteDataBaseHelper noteDB = new NoteDataBaseHelper(getContext());
                mTitleText = mEditTextTitle.getText().toString();
                mBodyText = mEditTextBody.getText().toString();
                if (mIsEditing == 1) {
                  noteDB.updateColumns(mTitleId,mTitleText,mBodyText);
                } else {
                    noteDB.addProject(mTitleText, mBodyText, 1);
                }
                MainFragment fragmentDone = MainFragment.newInstance(); // 遷移先
                FragmentTransaction fragmentTransactionDone = getFragmentManager().beginTransaction();
                fragmentTransactionDone.replace(R.id.linearLayout_container, fragmentDone);
                fragmentTransactionDone.addToBackStack(fragmentDone.getClass().getSimpleName());
                fragmentTransactionDone.commit();
                break;
            case R.id.button_cancel:
                MainFragment fragmentCancel = MainFragment.newInstance(); // 遷移先
                FragmentTransaction fragmentTransactionCancel = getFragmentManager().beginTransaction();
                fragmentTransactionCancel.replace(R.id.linearLayout_container, fragmentCancel);
                fragmentTransactionCancel.addToBackStack(fragmentCancel.getClass().getSimpleName());
                fragmentTransactionCancel.commit();
                break;
        }
    }
}
