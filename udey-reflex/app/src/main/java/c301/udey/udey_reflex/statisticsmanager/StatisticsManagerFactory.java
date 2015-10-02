package c301.udey.udey_reflex.statisticsmanager;

import android.content.Context;

import c301.udey.udey_reflex.Constants;
import c301.udey.udey_reflex.filestorage.CachedFileStorageManager;
import c301.udey.udey_reflex.filestorage.LocalFileStorageManager;

/**
 * Created by rishi on 15-10-02.
 */
public class StatisticsManagerFactory {

    public static BuzzerCountStatisticsManager getBuzzerCountStatisticsManager(Context context) {
        return new BuzzerCountStatisticsManager(new CachedFileStorageManager(new LocalFileStorageManager(context)), Constants.BUZZER_STATS_FILE_NAME);
    }

    public static ReactionTimeStatisticsManager getReactionTimeStatisticsManager(Context context) {
        return new ReactionTimeStatisticsManager(new CachedFileStorageManager(new LocalFileStorageManager(context)), Constants.REACTION_STATS_FILE_NAME);
    }
}
