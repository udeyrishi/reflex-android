package c301.udey.udey_reflex.statisticsmanager;

import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import c301.udey.udey_reflex.filestorage.FileStorageManager;

/**
 * Created by rishi on 15-09-27.
 */
public class ReactionTimeStatisticsManager {

    private static final int MAX_SAVE_COUNT = 100;
    private final String fileName;
    private final FileStorageManager storageManager;

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
        List<Long> delays = getLastNOrAllDelays(lastN);
        return delays.isEmpty() ? null : Collections.min(delays);
    }

    public Long getMaximumTime(int lastN) {
        List<Long> delays = getLastNOrAllDelays(lastN);
        return delays.isEmpty() ? null : Collections.max(delays);
    }

    public Double getAverageTime(int lastN) {
        List<Long> delays = getLastNOrAllDelays(lastN);

        if (delays.isEmpty()) {
            return null;
        }

        long sum = 0;
        for(Long delay : delays) {
            sum += delay;
        }
        return ((double)sum)/((double)delays.size());
    }

    public Double getMedianTime(int lastN) {

        // Udey Source: http://stackoverflow.com/questions/11955728/how-to-calculate-the-median-of-an-array
        List<Long> delays = getLastNOrAllDelays(lastN);

        if (delays.isEmpty()) {
            return null;
        }

        // To prevent the side effects of sorting in place for future calls
        // TODO: consider fixing CachedFileStorageManager to not be affected by this
        delays = new ArrayList<>(delays);
        Collections.sort(delays);

        double median;
        if (delays.size() % 2 == 0)
            median = ((double)delays.get(delays.size()/2) + (double)delays.get(delays.size()/2 - 1))/2;
        else
            median = (double) delays.get(delays.size()/2);

        return median;
    }

    private List<Long> getLastNOrAllDelays(int lastN) {
        ArrayList<Long> delays = safeGetSavedDelays();
        return (lastN >= delays.size()) ? delays : delays.subList(0, lastN - 1);
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
