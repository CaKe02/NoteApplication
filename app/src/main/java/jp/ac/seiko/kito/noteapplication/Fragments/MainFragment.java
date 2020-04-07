package jp.ac.seiko.kito.noteapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import jp.ac.seiko.kito.noteapplication.DataBase.NoteDataBaseHelper;
import jp.ac.seiko.kito.noteapplication.R;
import jp.ac.seiko.kito.noteapplication.RecyclerView.DataModel;
import jp.ac.seiko.kito.noteapplication.RecyclerView.RVAdapter;

public class MainFragment extends Fragment implements View.OnClickListener, RVAdapter.OnItemClickListener {

    private Button mButtonCreate;
    private LinearLayout mLinearLayoutTitle;
    RecyclerView mRecyclerViewList;

    private final static String KEY_FIGURE = "figure";
    private int mAmount = 0;



    NoteDataBaseHelper mNoteDataBaseHelper = new NoteDataBaseHelper(getContext());

    public static MainFragment newInstance(int figure) {
        MainFragment fragment = new MainFragment();
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
            mAmount = args.getInt(KEY_FIGURE, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        init(rootView); // 授業ではやらなかったけどこうするといい。
        return rootView;
    }

    private void init(View view) {
        mButtonCreate = view.findViewById(R.id.button_create);
        mRecyclerViewList = view.findViewById(R.id.recyclerView_main);
        mLinearLayoutTitle = view.findViewById(R.id.linearLayout_title);

        mButtonCreate.setOnClickListener(this);


        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerViewList.setHasFixedSize(false);
        mRecyclerViewList.setLayoutManager(manager);

        if (mAmount > 0) {
            mRecyclerViewList.setAdapter(new RVAdapter(createData()));
        }
    }

    public List<DataModel> createData() {
        List<DataModel> resultData = new ArrayList<>();
        for (int i = 1; i <= mAmount; i++) {
            DataModel row = new DataModel();
            String note[] = mNoteDataBaseHelper.getNoteByID(String.valueOf(i));
            String title = note[0];
            row.setTitle(title + i);
            resultData.add(row);
        }
        return resultData;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_create) {
            mButtonCreate.setText("hey");

            EditingFragment fragment = EditingFragment.newInstance(mAmount); // 遷移先
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.linearLayout_container, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onItemClick(String name) {
    }
}
