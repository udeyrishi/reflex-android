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

package c301.udey.udey_reflex.modes.practice;

import android.widget.Toast;

import java.io.IOException;

import c301.udey.udey_reflex.modes.FragmentsActivity;
import c301.udey.udey_reflex.statisticsmanager.ReactionTimeStatisticsManager;
import c301.udey.udey_reflex.statisticsmanager.StatisticsManagerFactory;

public class PracticeModeActivity extends FragmentsActivity
        implements PracticeModeCountdownFragment.OnCountdownFinishedListener,
        PracticeModeTapFragment.OnBuzzerTappedListener {

    private final static int MIN_DELAY_MILLISECONDS = 10;
    private final static int MAX_DELAY_MILLISECONDS = 2000;
    private final static int COUNTDOWN_SECONDS = 3;

    private ReactionTimeStatisticsManager statsManager;

    // Udey Source: http://stackoverflow.com/questions/2755277/android-hide-all-showed-toast-messages
    // Single toast suggestion
    private Toast statusToast;

    @Override
    protected void onStart() {
        super.onStart();
        statsManager = StatisticsManagerFactory.getReactionTimeStatisticsManager();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCountdown();
    }

    @Override
    public void onCountdownFinished() {
        swapFragments(PracticeModeTapFragment.newInstance(MIN_DELAY_MILLISECONDS, MAX_DELAY_MILLISECONDS));
    }

    @Override
    public void onBuzzerTapped(Long delay) {
        if (delay < 0) {
            showToast("Too soon!");
            onCountdownFinished();
        }
        else {
            saveDelayToStorage(delay);
            startCountdown();
            showToast("Response time:\n" + delay.toString() + " ms");
        }
    }

    private void saveDelayToStorage(Long delay) {
        try {
            statsManager.saveBuzzerDelay(delay);
        } catch (IOException e) {
            showToast("Failed to save this delay value in the stats.");
        }
    }

    private void startCountdown() {
        swapFragments(PracticeModeCountdownFragment.newInstance(COUNTDOWN_SECONDS));
    }

    private void showToast(String message) {
        if (statusToast == null) {
            // First time
            statusToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        }
        else {
            statusToast.setText(message);
        }
        statusToast.show();
    }
}
