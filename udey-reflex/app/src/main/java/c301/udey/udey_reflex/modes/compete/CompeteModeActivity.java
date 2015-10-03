/*
 * Copyright (C) 2015 Udey Rishi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        statsManager = StatisticsManagerFactory.getBuzzerCountStatisticsManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadButtonsScreen();
    }

    private void loadButtonsScreen() {
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
        loadButtonsScreen();
    }
}
