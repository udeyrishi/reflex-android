package c301.udey.udey_reflex.statisticsmanager;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

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
        HashMap<Integer, HashMap<String, Integer>> stats = safeGetStats();

        if (stats.containsKey(numberOfPlayers)) {
            HashMap<String, Integer> playerRecords = stats.get(numberOfPlayers);
            Integer currentValue = playerRecords.containsKey(playerWhoWon) ? playerRecords.get(playerWhoWon) : 0;
            playerRecords.put(playerWhoWon, currentValue + 1);
        }
        else {
            stats.put(numberOfPlayers, new HashMap<String, Integer>() {
                {
                    put(playerWhoWon, 1);
                }
            });
        }

        storageManager.save(stats, fileName, new TypeToken<HashMap<Integer, HashMap<String, Integer>>>(){}.getType());
    }

    public int getNumberOfBuzzes(Integer numberOfPlayers, String playerName) {
        HashMap<Integer, HashMap<String, Integer>> stats = safeGetStats();
        if (stats.containsKey(numberOfPlayers)) {
            HashMap<String, Integer> playerRecords = stats.get(numberOfPlayers);
            return playerRecords.containsKey(playerName) ? playerRecords.get(playerName) : 0;
        }
        else {
            return 0;
        }
    }

    private HashMap<Integer, HashMap<String, Integer>> safeGetStats() {
        HashMap<Integer, HashMap<String, Integer>> stats;
        try {
            stats = storageManager.load(fileName,
                    new TypeToken<HashMap<Integer, HashMap<String, Integer>>>() {}.getType());
        }
        catch (FileNotFoundException e) {
            stats = new HashMap<>();
        }
        return stats;
    }
}
