package c301.udey.udey_reflex.modes.practice;

import android.widget.Toast;

import java.io.IOException;

import c301.udey.udey_reflex.modes.FragmentsActivity;
import c301.udey.udey_reflex.modes.ResultFragment;
import c301.udey.udey_reflex.statisticsmanager.ReactionTimeStatisticsManager;
import c301.udey.udey_reflex.statisticsmanager.StatisticsManagerFactory;

public class PracticeModeActivity extends FragmentsActivity
        implements PracticeModeCountdownFragment.OnCountdownFinishedListener,
        PracticeModeTapFragment.OnBuzzerTappedListener,
        ResultFragment.OnResultDismissedListener {

    private final static int MIN_DELAY_MILLISECONDS = 10;
    private final static int MAX_DELAY_MILLISECONDS = 2000;
    private final static int COUNTDOWN_SECONDS = 3;

    private ReactionTimeStatisticsManager statsManager;

    @Override
    protected void onStart() {
        super.onStart();
        statsManager = StatisticsManagerFactory.getReactionTimeStatisticsManager(this);
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
        swapFragments(ResultFragment.newInstance(getResult(delay)));
    }

    @Override
    public void onTryAgain() {
        swapFragments(PracticeModeCountdownFragment.newInstance(COUNTDOWN_SECONDS));
    }

    private static CharSequence getResult(Long delayMilliseconds) {
        if (delayMilliseconds < 0) {
            return "Too soon!";
        }
        else {
            return "Response time:\n" + delayMilliseconds.toString() + " ms";
        }
    }
}
