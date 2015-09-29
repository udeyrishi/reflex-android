package c301.udey.udey_reflex.modes.compete;

import android.content.Intent;
import android.os.Bundle;

import c301.udey.udey_reflex.modes.FragmentsActivity;

public class CompeteModeActivity extends FragmentsActivity implements CompeteModeTapFragment.OnBuzzerTappedListener {

    private int numberOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        numberOfPlayers  = intent.getIntExtra(CompeteModeFragment.EXTRA_MESSAGE_NUMBER_PLAYERS, 0);
        if (numberOfPlayers < 2) {
            throw new IllegalArgumentException("Number of players can't be less than 2");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        swapFragments(CompeteModeTapFragment.newInstance(numberOfPlayers));
    }

    @Override
    public void onBuzzerTapped(int playerNumberWhoWon) {
        // Swap to show the result
    }
}
