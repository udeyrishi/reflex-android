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

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

/**
 * Created by rishi on 15-09-29.
 */
public class LocalFileStorageManager implements FileStorageManager {

    private final Context context;

    public LocalFileStorageManager(Context context) {
        this.context = context;
    }

    // saveToFile and loadFromFile adapted from lonelyTwitter lab project, CMPUT 301 Fall 2015
    // at University of Alberta
    // Udey Source: https://github.com/joshua2ua/lonelyTwitter/blob/master/app/src/main/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivity.java
    @Override
    public <T> void save(T obj, String fileName, Type typeOfT) throws IOException {
        FileOutputStream fos = context.openFileOutput(fileName, 0);
        OutputStreamWriter writer = new OutputStreamWriter(fos);
        Gson gson = new Gson();
        gson.toJson(obj, typeOfT, writer);
        writer.flush();
        fos.close();
    }

    @Override
    public <T> T load(String fileName, Type typeOfT) throws FileNotFoundException {
        FileInputStream fis = context.openFileInput(fileName);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        Gson gson = new Gson();
        // Following line from: https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
        return gson.fromJson(in, typeOfT);
    }

    @Override
    public void delete(String fileName) {
        context.deleteFile(fileName);
    }
}
