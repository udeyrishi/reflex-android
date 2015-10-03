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

    @Override
    public void delete(String fileName) {
        innerStorageManager.delete(fileName);
        if (cache.containsKey(fileName)) {
            cache.remove(fileName);
        }
    }
}
