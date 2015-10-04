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

import android.support.v4.app.Fragment;

/**
 * A base class for a {@link Fragment} that needs to be aware of when the fragment comes back in
 * focus.
 */
public class RefocusAwareFragment extends Fragment {

    /**
     * Callback for the event when the fragment comes back in focus.
     */
    public void onRefocus() {
        // Default implementation => don't do anything
    }
}
