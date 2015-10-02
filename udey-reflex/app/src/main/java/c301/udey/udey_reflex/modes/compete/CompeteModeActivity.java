package c301.udey.udey_reflex.modes.compete;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;

import c301.udey.udey_reflex.modes.FragmentsActivity;
import c301.udey.udey_reflex.modes.ResultFragment;
import c301.udey.udey_reflex.statisticsmanager.BuzzerCountStatisticsManager;
import c301.udey.udey_reflex.statisticsmanager.StatisticsManagerFactory;

public class CompeteModeActivity extends FragmentsActivity
        implements CompeteModeTapFragment.OnBuzzerTappedListener,
        ResultFragment.OnResultDismissedListener {

    private Integer numberOfPlayers;
    private BuzzerCountStatisticsManager statsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        numberOfPlayers  = intent.getIntExtra(CompeteModeFragment.EXTRA_MESSAGE_NUMBER_PLAYERS, 0);
        if (numberOfPlayers < 2) {
            throw new IllegalArgumentException("Number of players can't be less than 2");
        }
        statsManager = StatisticsManagerFactory.getBuzzerCountStatisticsManager(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        swapFragments(CompeteModeTapFragment.newInstance(numberOfPlayers));
    }

    @Override
    public void onBuzzerTapped(CharSequence playerWhoWon) {
        swapFragments(ResultFragment.newInstance(playerWhoWon + " won"));
        try {
            statsManager.registerBuzzerWin(numberOfPlayers, playerWhoWon.toString());
        }
        catch (IOException e) {
            Toast.makeText(this, "Failed to save this win in the stats.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTryAgain() {
        onBackPressed();
    }
}
