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

    private Boolean isActivityActive;

    @Override
    protected void onResume() {
        super.onResume();
        isActivityActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityActive = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);

        // ActionBar is present -- can't be null in this case
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    protected void swapFragments(Fragment newFragment) {
        // Keep the old fragment after the app is no longer in the foreground
        if (isActivityActive) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, newFragment)
                    .commit();
        }
    }
}
