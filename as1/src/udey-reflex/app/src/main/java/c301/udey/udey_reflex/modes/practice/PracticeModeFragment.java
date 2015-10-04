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

package c301.udey.udey_reflex.modes.practice;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import c301.udey.udey_reflex.MainActivity;
import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.modes.InstructionsFragment;

/**
 * A fragment showing the instructions for the PracticeMode.
 */
public class PracticeModeFragment extends InstructionsFragment {

    /**
     * Creates an instance of {@link PracticeModeFragment}.
     *
     * @param context       The callers context.
     * @param sectionNumber The section number where this fragment will be placed.
     * @return The generated PracticeModeFragment.
     */
    public static PracticeModeFragment getInstance(Context context, int sectionNumber) {

        PracticeModeFragment fragment = new PracticeModeFragment();
        fragment.setArguments(context.getString(R.string.practice_instructions));
        fragment.attachSectionNumber(sectionNumber);
        return fragment;
    }

    /**
     * The button press event handler that loads the {@link PracticeModeActivity}.
     * Corresponds the the state where user has read the instructions, and now wants to move on
     * to the game.
     *
     * @param v The button's view.
     */
    @Override
    protected void onButtonPress(View v) {
        Intent intent = new Intent(getActivity(), PracticeModeActivity.class);
        startActivity(intent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onSectionAttached((MainActivity) context);
    }
}
