package c301.udey.udey_reflex.modes.practice;

import android.widget.Toast;

import java.io.IOException;

import c301.udey.udey_reflex.Constants;
import c301.udey.udey_reflex.modes.FragmentsActivity;
import c301.udey.udey_reflex.statisticsmanager.ReactionTimeStatisticsManager;

public class PracticeModeActivity extends FragmentsActivity
        implements PracticeModeCountdownFragment.OnCountdownFinishedListener,
        PracticeModeTapFragment.OnBuzzerTappedListener,
        PracticeModeResultFragment.OnResultDismissedListener {

    private final static int MIN_DELAY_MILLISECONDS = 10;
    private final static int MAX_DELAY_MILLISECONDS = 2000;
    private final static int COUNTDOWN_SECONDS = 3;

    private ReactionTimeStatisticsManager statsManager;

    @Override
    protected void onStart() {
        super.onStart();
        statsManager = new ReactionTimeStatisticsManager(this, Constants.STATS_FILE_NAME);
        onTryAgain();
    }

    @Override
    public void onCountdownFinished() {
        swapFragments(PracticeModeTapFragment.newInstance(MIN_DELAY_MILLISECONDS, MAX_DELAY_MILLISECONDS));
    }

    @Override
    public void onBuzzerTapped(Long delay) {
        if (delay >= 0) {
            try {
                statsManager.saveBuzzerDelay(delay);
            } catch (IOException e) {
                Toast.makeText(this, "Failed to save this delay value in the stats.", Toast.LENGTH_SHORT).show();
            }
        }
        swapFragments(PracticeModeResultFragment.newInstance(delay));
    }

    @Override
    public void onTryAgain() {
        swapFragments(PracticeModeCountdownFragment.newInstance(COUNTDOWN_SECONDS));
    }
}
