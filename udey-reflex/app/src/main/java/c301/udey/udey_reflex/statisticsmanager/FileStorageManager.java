package c301.udey.udey_reflex.statisticsmanager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by rishi on 15-09-29.
 */
public interface FileStorageManager {
    <T> void save(T obj, String fileName, Type typeOfT) throws IOException;
    <T> T load(String fileName, Type typeOfT) throws FileNotFoundException;
}
