package c301.udey.udey_reflex.filestorage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by rishi on 2015-10-01.
 */
public class CachedFileStorageManager implements FileStorageManager {

    private final FileStorageManager innerStorageManager;
    HashMap<String, Object> cache;

    public CachedFileStorageManager(FileStorageManager innerStorageManager) {
        this.innerStorageManager = innerStorageManager;
        this.cache = new HashMap<>();
    }

    @Override
    public <T> void save(T obj, String fileName, Type typeOfT) throws IOException {
        innerStorageManager.save(obj, fileName, typeOfT);
        cache.put(fileName, obj);
    }

    @Override
    public <T> T load(String fileName, Type typeOfT) throws FileNotFoundException {
        if (!cache.containsKey(fileName)) {
            cache.put(fileName, innerStorageManager.load(fileName, typeOfT));
        }

        @SuppressWarnings("unchecked")
        T loadedValue = (T) cache.get(fileName);

        return loadedValue;
    }
}
