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

package c301.udey.udey_reflex;

import android.app.Application;
import android.content.Context;

/**
 * Class for accessing the app wide contexts.
 * Source: http://stackoverflow.com/questions/2002288/static-way-to-get-context-on-android
 */

public class ReflexApp extends Application {

    private static Context context;

    /**
     * Gets the app wide context. Should be used for non-UI actions.
     *
     * @return The app wide {@link Context}
     */
    public static Context getAppContext() {
        return ReflexApp.context;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        super.onCreate();
        ReflexApp.context = getApplicationContext();
    }
}
