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

import java.io.FileNotFoundException;
import java.lang.reflect.Type;

import c301.udey.udey_reflex.filestorage.FileStorageManager;

/**
 * Base class for statistics managers.
 */
public abstract class StatisticsManager {
    protected final String fileName;
    protected final FileStorageManager storageManager;

    /**
     * Creates a new instance of {@link StatisticsManager}.
     *
     * @param fileName       The file name to be used for persistance.
     * @param storageManager The {@link FileStorageManager} to be used for stats persistance.
     */
    public StatisticsManager(FileStorageManager storageManager, String fileName) {
        this.storageManager = storageManager;
        this.fileName = fileName;
    }

    /**
     * Clears all the stats.
     */
    public void clearStats() {
        storageManager.delete(fileName);
    }

    /**
     * Gets the data type in which the stats are stored.
     * @return
     */
    protected abstract Type getStorageFormatType();

    protected <T> T safeGetStats(T defaultObject) {
        T stats;
        try {
            stats = storageManager.load(fileName, getStorageFormatType());
        } catch (FileNotFoundException e) {
            stats = defaultObject;
        }
        return stats;
    }
}
