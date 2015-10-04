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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.RefocusAwareFragment;

/**
 * An abstract {@link RefocusAwareFragment} that contains a text view for showing some instructions,
 * and a button for moving to the next logical step.
 * Useful if the button press's callback logic resides in a fragment. In that case, extend this fragment.
 */
public abstract class InstructionsFragment extends RefocusAwareFragment {

    private CharSequence instructions;
    private View rootView;

    /**
     * Inflates the appropriate views, sets the instructions, and the button's callback.
     * The instructions must be set by the child classes through the {@link #setArguments(CharSequence)}
     * before this method is called.
     * @param inflater           The LayoutInflater.
     * @param container          The ViewGroup container that will contain the inflated view.
     * @param savedInstanceState The saved instance's state.
     * @return The inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_instructions, container, false);
        setButtonHandler();
        changeInstructionsInTextView();
        return rootView;
    }

    private void changeInstructionsInTextView() {
        TextView v = getInstructionsTextView();
        v.setText(instructions == null ? "" : instructions);
    }

    private void setButtonHandler() {
        Button b = getButton();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPress(v);
            }
        });
    }

    /**
     * Sets the arguments needed by this class, i.e., the text in the instructions.
     * This method shall be called immediately after construction, before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} is called by Android.
     * @param instructions The instructions to be set.
     */
    protected void setArguments(CharSequence instructions) {
        this.instructions = instructions;
    }

    /**
     * The callback for the button press.
     * @param v The button's view.
     */
    protected abstract void onButtonPress(View v);

    /**
     * Gets a reference to the text view containing the instructions for more specific tweaking.
     * @return The {@link TextView}
     */
    protected TextView getInstructionsTextView() {
        return (TextView) rootView.findViewById(R.id.instructions_text_view);
    }

    /**
     * Gets a reference to the fragment's button for more specific tweaking.
     * @return The {@link Button}
     */
    protected Button getButton() {
        return (Button) rootView.findViewById(R.id.finish_instructions_button);
    }
}
