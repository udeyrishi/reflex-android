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

/**
 * Created by rishi on 15-09-27.
 */
public class ReactionTimeStatisticsManager {

    private static final int MAX_SAVE_COUNT = 100;

    private final String fileName;
    private final Context context;

    public ReactionTimeStatisticsManager(Context context, String fileName) {
        this.fileName = fileName;
        this.context = context;
    }

    public void saveBuzzerDelay(Long delay) throws IOException {
        ArrayList<Long> savedDelays = loadFromFile();
        while (savedDelays.size() >= 100) {
            savedDelays.remove(0);
        }
        savedDelays.add(delay);
        saveToFile(savedDelays);
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


    // saveToFile and loadFromFile adapted from lonelyTwitter lab project, CMPUT 301 Fall 2015
    // at University of Alberta
    // Udey Source: https://github.com/joshua2ua/lonelyTwitter/blob/master/app/src/main/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivity.java
    private void saveToFile(ArrayList<Long> delays) throws IOException {
        FileOutputStream fos = context.openFileOutput(fileName, 0);
        OutputStreamWriter writer = new OutputStreamWriter(fos);
        Gson gson = new Gson();
        gson.toJson(delays, writer);
        writer.flush();
        fos.close();
    }

    private ArrayList<Long> loadFromFile(){
        FileInputStream fis = null;

        try {
            fis = context.openFileInput(fileName);
        } catch (FileNotFoundException e) {
            return new ArrayList<Long>();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        Gson gson = new Gson();

        // Following line from: https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
        return gson.fromJson(in, new TypeToken<ArrayList<Long>>(){}.getType());
    }
}
