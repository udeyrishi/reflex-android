package c301.udey.udey_reflex.modes.compete;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

import c301.udey.udey_reflex.Constants;
import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.modes.AppModeActivity;
import c301.udey.udey_reflex.modes.practice.PracticeModeCountdownFragment;
import c301.udey.udey_reflex.modes.practice.PracticeModeResultFragment;
import c301.udey.udey_reflex.modes.practice.PracticeModeTapFragment;
import c301.udey.udey_reflex.statisticsmanager.ReactionTimeStatisticsManager;

public class CompeteModeActivity extends AppModeActivity implements CompeteModeTapFragment.OnBuzzerTappedListener {

    private int numberOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        numberOfPlayers  = intent.getIntExtra(CompeteModeFragment.EXTRA_MESSAGE_NUMBER_PLAYERS, 0);
        if (numberOfPlayers < 2) {
            throw new IllegalArgumentException("Number of players can't be less than 2");
        }
        setContentView(R.layout.activity_compete_mode);
    }

    @Override
    protected void onStart() {
        super.onStart();
        swapFragments(R.id.compete_mode_container, CompeteModeTapFragment.newInstance(numberOfPlayers));
    }

    @Override
    public void onBuzzerTapped(int playerNumberWhoWon) {
        // Swap to show the result
    }
}
