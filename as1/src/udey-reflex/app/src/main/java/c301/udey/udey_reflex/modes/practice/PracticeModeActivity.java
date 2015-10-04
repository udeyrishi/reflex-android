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

import android.support.v7.internal.app.ToolbarActionBar;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import c301.udey.udey_reflex.modes.FragmentsActivity;
import c301.udey.udey_reflex.statisticsmanager.ReactionTimeStatisticsManager;
import c301.udey.udey_reflex.statisticsmanager.StatisticsManagerFactory;

/**
 * Activity corresponding to the the practice (single-player) mode.
 */
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onStart() {
        super.onStart();
        statsManager = StatisticsManagerFactory.getReactionTimeStatisticsManager();
    }

    /**
     * Imitates the pressing of the back button, and hence goes back to the view before this activity.
     * Useful, because the user shouldn't just see the countdown ticking automatically when they come
     * back to the app.
     */
    @Override
    protected void onPause() {
        super.onPause();
        onBackPressed();
    }

    /**
     * Starts the countdown before the buzzer appears.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startCountdown();
    }

    /**
     * Loads the {@link PracticeModeTapFragment}.
     */
    @Override
    public void onCountdownFinished() {
        swapFragments(PracticeModeTapFragment.newInstance(MIN_DELAY_MILLISECONDS, MAX_DELAY_MILLISECONDS));
    }

    /**
     * Callback for the buzzer. Displays the result in a toast.
     *
     * @param delayInMilliseconds The delay value between the buzzer being activated, and the user pressing it.
     *              A negative value implies that the buzzer was pressed too soon. The random wait
     *              timer (before the buzzer is activated) will be restarted.
     *              A positive value will display the result, and go back to the countdown screen.
     */
    @Override
    public void onBuzzerTapped(Long delayInMilliseconds) {
        if (delayInMilliseconds < 0) {
            showToast("Too soon!");
            onCountdownFinished();
        } else {
            saveDelayToStorage(delayInMilliseconds);
            startCountdown();
            showToast("Reaction time: " + delayInMilliseconds.toString() + " ms");
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
        if (statusToast != null) {
            statusToast.cancel();
        }
        statusToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        statusToast.show();
    }
}
