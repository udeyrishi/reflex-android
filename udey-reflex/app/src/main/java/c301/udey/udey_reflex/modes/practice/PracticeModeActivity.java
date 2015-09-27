package c301.udey.udey_reflex.modes.practice;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import at.markushi.ui.CircleButton;
import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.modes.AppModesProvider;

public class PracticeModeActivity extends AppCompatActivity
        implements PracticeModeCountdownFragment.OnCountdownFinishedListener, PracticeModeTapFragment.OnBuzzerTappedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_mode);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.practice_mode_container, PracticeModeCountdownFragment.newInstance("foo", "bar"))
                .commit();
    }

    @Override
    public void onCountdownFinished() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.practice_mode_container, PracticeModeTapFragment.newInstance("foo", "bar"))
                .commit();
    }

    @Override
    public void onBuzzerTapped(Long delay) {
        Toast.makeText(this, "Delay: " + delay.toString(), Toast.LENGTH_SHORT).show();
    }
}
