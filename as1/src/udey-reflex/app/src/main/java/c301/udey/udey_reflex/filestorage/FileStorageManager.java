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

/**
 * An interface for a manager that allows some kind of file persistance.
 */
public interface FileStorageManager {

    /**
     * Saves the file to the persistant storage.
     *
     * @param obj      The object to be stored.
     * @param fileName The name of the file.
     * @param typeOfT  The Type corresponding to the generic type T.
     * @param <T>      The generic type of the object to be stored.
     * @throws IOException Thrown if I/O fails.
     */
    <T> void save(T obj, String fileName, Type typeOfT) throws IOException;

    /**
     * Loads the file from persistance storage.
     *
     * @param fileName The name of the file.
     * @param typeOfT  The Type corresponding to the generic type T.
     * @param <T>      The generic type of the object to be stored.
     * @return The loaded object.
     * @throws FileNotFoundException Thrown if the file is not found.
     */
    <T> T load(String fileName, Type typeOfT) throws FileNotFoundException;

    /**
     * Deletes the file if it exists.
     *
     * @param fileName The name of the file to be deleted.
     */
    void delete(String fileName);
}
