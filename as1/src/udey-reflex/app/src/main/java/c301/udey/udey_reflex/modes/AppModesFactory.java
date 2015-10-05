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

package c301.udey.udey_reflex.modes;

import android.content.Context;

import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.SectionFragment;
import c301.udey.udey_reflex.modes.compete.CompeteModeFragment;
import c301.udey.udey_reflex.modes.practice.PracticeModeFragment;
import c301.udey.udey_reflex.modes.stats.StatsModeFragment;

/**
 * A factory for the different app modes supported by the app.
 */
public class AppModesFactory {

    /**
     * Gets the names of all the modes supported by the app.
     *
     * @param context The context to be used for getting the string resources.
     * @return The array of Strings corresponding to the app mode names.
     */
    public static String[] getAppModes(Context context) {
        return new String[]{
                context.getString(R.string.section_name_practice),
                context.getString(R.string.section_name_compete),
                context.getString(R.string.section_name_stats)
        };
    }

    /**
     * Gets the {@link SectionFragment} corresponding to the app mode specified by the
     * sectionNumber.
     *
     * @param context       The caller's context.
     * @param sectionNumber The section number to be attached to the fragment. This section number
     *                      is the index in the navigation pane, and correspond to the
     *                      index in the array that is the return value of {@link #getAppModes(Context)}.
     * @return The appropriate fragment.
     */
    public static SectionFragment getAppModeFragment(Context context, int sectionNumber) {
        switch (sectionNumber) {
            case 0:
                return PracticeModeFragment.getInstance(context, sectionNumber);
            case 1:
                return CompeteModeFragment.getInstance(context, sectionNumber);
            case 2:
                return StatsModeFragment.getInstance(sectionNumber);
            default:
                throw new IllegalArgumentException("No app mode defined at this index.");
        }
    }
}
