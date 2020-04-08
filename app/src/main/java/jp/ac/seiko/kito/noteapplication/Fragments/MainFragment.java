package jp.ac.seiko.kito.noteapplication.Fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private TextView mTextViewTitleId;
    private LinearLayout mLinearLayoutTitle;
    RecyclerView mRecyclerViewList;

    private final static String KEY_FIGURE = "figure";
    private int mAmount = 0;
    private int mFigure = 0;





    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        init(rootView); // 授業ではやらなかったけどこうするといい。
        return rootView;
    }

    private void init(View view) {
        mButtonCreate = view.findViewById(R.id.button_create);
        mTextViewTitleId = view.findViewById(R.id.textView_titleId);
        mRecyclerViewList = view.findViewById(R.id.recyclerView_main);
        mLinearLayoutTitle = view.findViewById(R.id.linearLayout_title);

        mButtonCreate.setOnClickListener(this);



        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerViewList.setHasFixedSize(false);
        mRecyclerViewList.setLayoutManager(manager);

        NoteDataBaseHelper noteDB = new NoteDataBaseHelper(getContext());
        List<Integer> list = noteDB.getAllID(1);
        try {
            mAmount = list.size();
        } catch (Exception e) {

        }

        if (mAmount > 0) {

            RVAdapter adapter = new RVAdapter(createData());
            adapter.setOnItemClickListener(this);
            mRecyclerViewList.setAdapter(adapter);
        }


    }

    public List<DataModel> createData() {
        NoteDataBaseHelper noteDB = new NoteDataBaseHelper(getContext());
        List<Integer> idList = noteDB.getAllID(1);
        List<DataModel> resultData = new ArrayList<>();
        for (int i = 1; i <= mAmount; i++) {
            DataModel row = new DataModel();
            String note[] = noteDB.getNoteByID(idList.get(i-1));
            String title = note[0];
            row.setTitle(title);

            row.setTitleId(String.valueOf(idList.get(i-1)));
            resultData.add(row);
        }
        return resultData;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_create) {
            EditingFragment fragment = EditingFragment.newInstance(0,null,null,0); // 遷移先
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.linearLayout_container, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onItemClick(String id, int isDeleteButton) {
        NoteDataBaseHelper noteDB = new NoteDataBaseHelper(getContext());
        if (isDeleteButton == 0) {
            int titleId = Integer.parseInt(id);
            String title = noteDB.getNoteByID(titleId)[0];
            String body = noteDB.getNoteByID(titleId)[1];
            EditingFragment fragment = EditingFragment.newInstance(titleId, title, body, 1); // 遷移先
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.linearLayout_container, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commit();
        } else {
            noteDB.deleteProject(id);
            MainFragment mainFragment = MainFragment.newInstance();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.linearLayout_container, mainFragment);
            fragmentTransaction.addToBackStack(mainFragment.getClass().getSimpleName());
            fragmentTransaction.commit();
        }

    }
}
