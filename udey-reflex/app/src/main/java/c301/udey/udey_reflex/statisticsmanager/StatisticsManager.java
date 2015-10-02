package c301.udey.udey_reflex.statisticsmanager;

import android.content.Context;

import java.util.ArrayList;

import c301.udey.udey_reflex.R;

/**
 * Created by rishi on 15-10-02.
 */
public class StatisticsManager {

    private final Context context;
    private final BuzzerCountStatisticsManager buzzerCountStatisticsManager;
    private final ReactionTimeStatisticsManager reactionTimeStatisticsManager;

    public StatisticsManager(Context context) {
        this.context = context;
        buzzerCountStatisticsManager = StatisticsManagerFactory.getBuzzerCountStatisticsManager(context);
        reactionTimeStatisticsManager = StatisticsManagerFactory.getReactionTimeStatisticsManager(context);
    }

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

    public void clearBuzzerCountStats() {
        buzzerCountStatisticsManager.clearStats();
    }

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

    public void clearReactionTimeStats() {
        reactionTimeStatisticsManager.clearStats();
    }
}
