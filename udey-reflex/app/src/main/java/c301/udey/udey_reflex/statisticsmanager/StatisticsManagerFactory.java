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


import c301.udey.udey_reflex.Constants;
import c301.udey.udey_reflex.ReflexApp;
import c301.udey.udey_reflex.filestorage.CachedFileStorageManager;
import c301.udey.udey_reflex.filestorage.LocalFileStorageManager;

/**
 * Created by rishi on 15-10-02.
 */
public class StatisticsManagerFactory {

    public static BuzzerCountStatisticsManager getBuzzerCountStatisticsManager() {
        return new BuzzerCountStatisticsManager(
                new CachedFileStorageManager(
                        new LocalFileStorageManager(ReflexApp.getAppContext())),
                Constants.BUZZER_STATS_FILE_NAME);
    }

    public static ReactionTimeStatisticsManager getReactionTimeStatisticsManager() {
        return new ReactionTimeStatisticsManager(
                new CachedFileStorageManager(
                        new LocalFileStorageManager(ReflexApp.getAppContext())),
                Constants.REACTION_STATS_FILE_NAME);
    }
}
