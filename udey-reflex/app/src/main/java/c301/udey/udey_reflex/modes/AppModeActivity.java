package c301.udey.udey_reflex.modes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rishi on 15-09-28.
 */
public class AppModeActivity extends AppCompatActivity {

    protected void swapFragments(int targetFrameLayout, Fragment newFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(targetFrameLayout, newFragment)
                .commit();
    }
}
