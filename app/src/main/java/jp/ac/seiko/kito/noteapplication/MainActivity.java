package jp.ac.seiko.kito.noteapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.LinearLayout;

import jp.ac.seiko.kito.noteapplication.DataBase.NoteDataBaseHelper;
import jp.ac.seiko.kito.noteapplication.Fragments.EditingFragment;
import jp.ac.seiko.kito.noteapplication.Fragments.MainFragment;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mLinearLayoutC;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        mLinearLayoutC = findViewById(R.id.linearLayout_container);
        MainFragment mainFragment = MainFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.linearLayout_container, mainFragment);
        fragmentTransaction.addToBackStack(mainFragment.getClass().getSimpleName());
        fragmentTransaction.commit();

    }
}
