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

package c301.udey.udey_reflex.statisticsmanager;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import c301.udey.udey_reflex.R;

/**
 * A statistic manager for the stats needed by the ReflexApp.
 */
public class ReflexStatisticsManager {

    private final Context context;
    private final BuzzerCountStatisticsManager buzzerCountStatisticsManager;
    private final ReactionTimeStatisticsManager reactionTimeStatisticsManager;

    /**
     * Creates a new instance of {@link ReflexStatisticsManager}.
     * @param context The context for getting the string resources.
     */
    public ReflexStatisticsManager(Context context) {
        this.context = context;
        buzzerCountStatisticsManager = StatisticsManagerFactory.getBuzzerCountStatisticsManager();
        reactionTimeStatisticsManager = StatisticsManagerFactory.getReactionTimeStatisticsManager();
    }

    /**
     * Gets a list of buzzer count stats.
     * @return List of buzzer count stats.
     */
    public ArrayList<Statistic<Long>> getBuzzerCountStats() {
        ArrayList<Statistic<Long>> buzzerTimeStats = new ArrayList<>();

        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(2,
                context.getString(R.string.player_one)));
        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(2,
                context.getString(R.string.player_two)));

        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(3,
                context.getString(R.string.player_one)));
        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(3,
                context.getString(R.string.player_two)));
        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(3,
                context.getString(R.string.player_three)));

        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(4,
                context.getString(R.string.player_one)));
        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(4,
                context.getString(R.string.player_two)));
        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(4,
                context.getString(R.string.player_three)));
        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(4,
                context.getString(R.string.player_four)));

        return buzzerTimeStats;
    }

    /**
     * Deletes all the buzzer count stats.
     */
    public void clearBuzzerCountStats() {
        buzzerCountStatisticsManager.clearStats();
    }

    /**
     * Gets a list of reaction time stats.
     * @return List of reaction time stats.
     */
    public ArrayList<Statistic<? extends Number>> getReactionTimeStats() {
        ArrayList<Statistic<? extends Number>> reactionTimeStats = new ArrayList<>();

        reactionTimeStats.add(reactionTimeStatisticsManager.getMinimumTime(10));
        reactionTimeStats.add(reactionTimeStatisticsManager.getMinimumTime(100));

        reactionTimeStats.add(reactionTimeStatisticsManager.getMaximumTime(10));
        reactionTimeStats.add(reactionTimeStatisticsManager.getMaximumTime(100));

        reactionTimeStats.add(reactionTimeStatisticsManager.getAverageTime(10));
        reactionTimeStats.add(reactionTimeStatisticsManager.getAverageTime(100));

        reactionTimeStats.add(reactionTimeStatisticsManager.getMedianTime(10));
        reactionTimeStats.add(reactionTimeStatisticsManager.getMedianTime(100));

        return reactionTimeStats;
    }

    /**
     * Deletes all the reaction time stats.
     */
    public void clearReactionTimeStats() {
        reactionTimeStatisticsManager.clearStats();
    }

    /**
     * Creates an intent for sending an email containing all the stats.
     * @param emailSubject The emails subject.
     * @return The created intent.
     */
    public Intent getStatsEmailIntent(String emailSubject) {
        // Source: http://stackoverflow.com/questions/6583010/how-to-create-email-button-on-android
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, getAllStatsSerialized());
        return emailIntent;
    }

    private String getAllStatsSerialized() {

        StringBuilder allStats = new StringBuilder("Reaction time stats:\n");
        for (Statistic<? extends Number> statistic : getReactionTimeStats()) {
            allStats.append(statistic.toString()).append("\n");
        }

        allStats.append("\n\nBuzzer count stats:\n");
        for (Statistic<Long> statistic : getBuzzerCountStats()) {
            allStats.append(statistic.toString()).append("\n");
        }

        return allStats.toString();
    }
}
