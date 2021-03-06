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

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import c301.udey.udey_reflex.filestorage.FileStorageManager;

/**
 * A stats manager for the practice mode reaction times.
 */
public class ReactionTimeStatisticsManager extends StatisticsManager {

    private static final int MAX_SAVE_COUNT = 100;

    /**
     * Creates a new instance of {@link ReactionTimeStatisticsManager}.
     *
     * @param storageManager The {@link FileStorageManager} to be used for stats persistance.
     * @param fileName       The file name to be used for persistance.
     */
    public ReactionTimeStatisticsManager(FileStorageManager storageManager, String fileName) {
        super(storageManager, fileName);
    }

    /**
     * Saves a new delayInMilliseconds value. Only stores the last 100 stats, and keeps
     * deleting old ones when this threshold is crossed.
     *
     * @param delayInMilliseconds The delay value in milliseconds.
     * @throws IOException Thrown by {@link FileStorageManager#save(Object, String, Type)}.
     */
    public void saveBuzzerDelay(Long delayInMilliseconds) throws IOException {
        ArrayList<Long> savedDelays = safeGetSavedDelays();

        while (savedDelays.size() >= MAX_SAVE_COUNT) {
            savedDelays.remove(0);
        }
        savedDelays.add(delayInMilliseconds);
        storageManager.save(savedDelays, fileName, new TypeToken<ArrayList<Long>>() {
        }.getType());
    }

    /**
     * Gets the minimum delay time over the last min(lastN, 100) plays.
     *
     * @param lastN The number of stats to be analysed. If lastN > 100, then analyses last 100 stats.
     * @return The minimum delay time.
     */
    public Statistic<Long> getMinimumTime(int lastN) {
        List<Long> delays = getLastNOrAllDelays(lastN);
        Long minTime = delays.isEmpty() ? null : Collections.min(delays);
        return new Statistic<>(String.format("Min reaction time over last %d tries", lastN), minTime);
    }

    /**
     * Gets the maximum delay time over the last min(lastN, 100) plays.
     *
     * @param lastN The number of stats to be analysed. If lastN > 100, then analyses last 100 stats.
     * @return The maximum delay time.
     */
    public Statistic<Long> getMaximumTime(int lastN) {
        List<Long> delays = getLastNOrAllDelays(lastN);
        Long maxTime = delays.isEmpty() ? null : Collections.max(delays);
        return new Statistic<>(String.format("Max reaction time over last %d tries", lastN), maxTime);
    }

    /**
     * Gets the average delay time over the last min(lastN, 100) plays.
     *
     * @param lastN The number of stats to be analysed. If lastN > 100, then analyses last 100 stats.
     * @return The average delay time.
     */
    public Statistic<Double> getAverageTime(int lastN) {
        List<Long> delays = getLastNOrAllDelays(lastN);

        Double averageTime;
        if (delays.isEmpty()) {
            averageTime = null;
        } else {

            long sum = 0;
            for (Long delay : delays) {
                sum += delay;
            }
            averageTime = ((double) sum) / ((double) delays.size());
        }

        return new Statistic<>(String.format("Average reaction time over last %d tries", lastN), averageTime);
    }

    /**
     * Gets the median delay time over the last min(lastN, 100) plays.
     *
     * @param lastN The number of stats to be analysed. If lastN > 100, then analyses last 100 stats.
     * @return The median delay time.
     */
    public Statistic<Double> getMedianTime(int lastN) {

        // Algorithm borrowed from:
        // Source: http://stackoverflow.com/questions/11955728/how-to-calculate-the-median-of-an-array
        List<Long> delays = getLastNOrAllDelays(lastN);

        Double median;
        if (delays.isEmpty()) {
            median = null;
        } else {

            // To prevent the side effects of sorting in place for future calls
            // TODO: consider fixing CachedFileStorageManager to not be affected by this
            delays = new ArrayList<>(delays);
            Collections.sort(delays);

            if (delays.size() % 2 == 0)
                median = ((double) delays.get(delays.size() / 2) + (double) delays.get(delays.size() / 2 - 1)) / 2;
            else
                median = (double) delays.get(delays.size() / 2);
        }

        return new Statistic<>(String.format("Median reaction time over last %d tries", lastN), median);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Type getStorageFormatType() {
        return new TypeToken<ArrayList<Long>>() {}.getType();
    }

    private List<Long> getLastNOrAllDelays(int lastN) {
        ArrayList<Long> delays = safeGetSavedDelays();
        return (lastN >= delays.size()) ? delays : delays.subList(0, lastN - 1);
    }

    private ArrayList<Long> safeGetSavedDelays() {
        return safeGetStats(new ArrayList<Long>());
    }
}
