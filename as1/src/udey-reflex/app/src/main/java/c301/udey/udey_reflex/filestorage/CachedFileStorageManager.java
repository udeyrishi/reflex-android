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

package c301.udey.udey_reflex.filestorage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link FileStorageManager} wrapper that wraps around another FileStorageManager
 * to keep the data retrieved and sent to/from the inner FileStorageManager in-memory.
 * Useful to keep the data in-memory for repeated data accesses.
 */
public class CachedFileStorageManager implements FileStorageManager {

    private final FileStorageManager innerStorageManager;
    private final Map<String, Object> cache;

    /**
     * Creates a new instance of the {@link CachedFileStorageManager}.
     *
     * @param innerStorageManager The inner {@link FileStorageManager} to be used for I/O.
     */
    public CachedFileStorageManager(FileStorageManager innerStorageManager) {
        this.innerStorageManager = innerStorageManager;
        this.cache = new HashMap<>();
    }

    /**
     * Saves the file to the persistant storage and cache it.
     *
     * @param obj      The object to be stored.
     * @param fileName The name of the file.
     * @param typeOfT  The Type corresponding to the generic type T.
     * @param <T>      The generic type of the object to be stored.
     * @throws IOException Thrown if I/O fails.
     */
    @Override
    public <T> void save(T obj, String fileName, Type typeOfT) throws IOException {
        innerStorageManager.save(obj, fileName, typeOfT);
        cache.put(fileName, obj);
    }

    /**
     * Loads the file from persistance storage and cache it.
     *
     * @param fileName The name of the file.
     * @param typeOfT  The Type corresponding to the generic type T.
     * @param <T>      The generic type of the object to be stored.
     * @return The loaded object.
     * @throws FileNotFoundException Thrown if the file is not found.
     */
    @Override
    public <T> T load(String fileName, Type typeOfT) throws FileNotFoundException {
        if (!cache.containsKey(fileName)) {
            cache.put(fileName, innerStorageManager.load(fileName, typeOfT));
        }

        @SuppressWarnings("unchecked")
        T loadedValue = (T) cache.get(fileName);

        return loadedValue;
    }

    /**
     * Deletes the file if it exists, and remove it from the cache.
     *
     * @param fileName The name of the file to be deleted.
     */
    @Override
    public void delete(String fileName) {
        innerStorageManager.delete(fileName);
        if (cache.containsKey(fileName)) {
            cache.remove(fileName);
        }
    }
}
