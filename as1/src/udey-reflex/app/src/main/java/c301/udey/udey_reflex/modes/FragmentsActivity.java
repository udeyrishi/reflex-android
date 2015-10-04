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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import c301.udey.udey_reflex.R;

/**
 * A base class for an activity that contains multiple fragments.
 */
public class FragmentsActivity extends AppCompatActivity {

    private Boolean isActivityActive;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onResume() {
        super.onResume();
        isActivityActive = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPause() {
        super.onPause();
        isActivityActive = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);

        // ActionBar is present -- can't be null in this case. So safe to suppress.
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    /**
     * Swaps the fragment with the new fragment, if the activity is still in foreground.
     * Does nothing if the activity is not in the foreground ({@link #onPause()} was called).
     * @param newFragment The new fragment to be put up in the fragment container of the layout.
     */
    protected void swapFragments(Fragment newFragment) {
        // Keep the old fragment after the app is no longer in the foreground
        if (isActivityActive) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, newFragment)
                    .commit();
        }
    }
}
