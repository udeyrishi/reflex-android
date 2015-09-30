package c301.udey.udey_reflex.statisticsmanager;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import c301.udey.udey_reflex.Constants;

/**
 * Created by rishi on 15-09-27.
 */
public class ReactionTimeStatisticsManager {

    private static final int MAX_SAVE_COUNT = 100;
    private final String fileName;
    private final FileStorageManager  storageManager;

    public ReactionTimeStatisticsManager(FileStorageManager storageManager, String fileName) {
        this.fileName = fileName;
        this.storageManager = storageManager;
    }

    public void saveBuzzerDelay(Long delay) throws IOException {
        ArrayList<Long> savedDelays = safeGetSavedDelays();

        while (savedDelays.size() >= MAX_SAVE_COUNT) {
            savedDelays.remove(0);
        }
        savedDelays.add(delay);
        storageManager.save(savedDelays, fileName, new TypeToken<ArrayList<Long>>(){}.getType());
    }

    public Long getMinimumTime(int lastN) {
        return null;
    }

    public Long getMaximumTime(int lastN) {
        return null;
    }

    public Long getAverageTime(int lastN) {
        return null;
    }

    public Long getMedianTime(int lastN) {
        return null;
    }

    private ArrayList<Long> safeGetSavedDelays() {
        ArrayList<Long> savedDelays;
        try {
            savedDelays = storageManager.load(fileName, new TypeToken<ArrayList<Long>>(){}.getType());
        }
        catch (FileNotFoundException e){
            savedDelays = new ArrayList<>();
        }
        return savedDelays;
    }
}
