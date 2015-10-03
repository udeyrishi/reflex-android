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
import android.support.v4.app.Fragment;

import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.modes.compete.CompeteModeFragment;
import c301.udey.udey_reflex.modes.practice.PracticeModeFragment;
import c301.udey.udey_reflex.modes.stats.StatsModeFragment;

/**
 * Created by rishi on 15-09-26.
 */
public class AppModesProvider {

    public static String[] getAppModes(Context context) {
        return new String[] {
                context.getString(R.string.section_name_practice),
                context.getString(R.string.section_name_compete),
                context.getString(R.string.section_name_stats)
        };
    }

    public static Fragment selectAppMode(Context context, int sectionNumber) {
        switch(sectionNumber) {
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
