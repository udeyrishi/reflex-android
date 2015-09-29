package c301.udey.udey_reflex.modes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import c301.udey.udey_reflex.R;

/**
 * Created by rishi on 15-09-28.
 */
public class FragmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
    }

    protected void swapFragments(Fragment newFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, newFragment)
                .commit();
    }
}
