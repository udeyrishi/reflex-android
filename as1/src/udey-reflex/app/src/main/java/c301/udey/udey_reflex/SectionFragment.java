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

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * A base class for a {@link Fragment} that acts as a section filler that can be swapped in or out.
 * Needs to be aware of when the fragment comes back in focus.
 */
public class SectionFragment extends Fragment {

    protected static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Callback for the event when the fragment comes back in focus.
     */
    public void onRefocus() {
        // Default implementation => don't do anything
    }

    /**
     * Configures the fragment to contain the section number as an argument value.
     *
     * @param sectionNumber The section number to be used.
     */
    protected void attachSectionNumber(int sectionNumber) {
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        this.setArguments(args);
    }

    /**
     * Passes the fragment's section number (configured earlier by {@link #attachSectionNumber(int)})
     * to the {@link SectionHolder#onSectionAttached(int)} method.
     *
     * @param holder The {@link SectionHolder} whose {@link SectionHolder#onSectionAttached(int)}
     *               method is to be called.
     */
    protected void onSectionAttached(SectionHolder holder) {
        holder.onSectionAttached(this.getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
