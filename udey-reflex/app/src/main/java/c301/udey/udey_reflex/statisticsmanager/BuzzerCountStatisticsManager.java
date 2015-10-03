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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import c301.udey.udey_reflex.filestorage.FileStorageManager;

/**
 * Created by rishi on 15-09-27.
 */
public class BuzzerCountStatisticsManager {

    private final FileStorageManager storageManager;
    private final String fileName;

    public BuzzerCountStatisticsManager(FileStorageManager storageManager, String fileName) {
        this.storageManager = storageManager;
        this.fileName = fileName;
    }

    public void registerBuzzerWin(Integer numberOfPlayers, final String playerWhoWon) throws IOException {
        HashMap<Integer, HashMap<String, Long>> stats = safeGetStats();

        if (stats.containsKey(numberOfPlayers)) {
            HashMap<String, Long> playerRecords = stats.get(numberOfPlayers);
            Long currentValue = playerRecords.containsKey(playerWhoWon) ? playerRecords.get(playerWhoWon) : 0;
            playerRecords.put(playerWhoWon, currentValue + 1);
        }
        else {
            stats.put(numberOfPlayers, new HashMap<String, Long>() {
                {
                    put(playerWhoWon, new Long(1));
                }
            });
        }

        storageManager.save(stats, fileName, new TypeToken<HashMap<Integer, HashMap<String, Integer>>>(){}.getType());
    }

    public Statistic<Long> getNumberOfBuzzes(Integer numberOfPlayers, String playerName) {
        HashMap<Integer, HashMap<String, Long>> stats = safeGetStats();

        Long numberOfBuzzes;
        if (stats.containsKey(numberOfPlayers)) {
            HashMap<String, Long> playerRecords = stats.get(numberOfPlayers);
            numberOfBuzzes = playerRecords.containsKey(playerName) ? playerRecords.get(playerName) : 0;
        }
        else {
            numberOfBuzzes = new Long(0);
        }

        return new Statistic<>(String.format("%d player mode, %s buzzes", numberOfPlayers, playerName), numberOfBuzzes);
    }

    public void clearStats() {
        storageManager.delete(fileName);
    }

    private HashMap<Integer, HashMap<String, Long>> safeGetStats() {
        HashMap<Integer, HashMap<String, Long>> stats;
        try {
            stats = storageManager.load(fileName,
                    new TypeToken<HashMap<Integer, HashMap<String, Long>>>() {}.getType());
        }
        catch (FileNotFoundException e) {
            stats = new HashMap<>();
        }
        return stats;
    }
}
