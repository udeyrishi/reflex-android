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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import c301.udey.udey_reflex.R;

/**
 * Created by rishi on 15-09-27.
 */
public abstract class InstructionsFragment extends Fragment {

    private CharSequence instructions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);
        setButtonHandler(rootView);
        changeInstructionsInTextView(rootView);
        return rootView;
    }

    private void changeInstructionsInTextView(View rootView) {
        TextView v = getInstructionsTextView(rootView);
        v.setText(instructions == null ? "" : instructions);
    }

    private void setButtonHandler(View rootView) {
        Button b = getButton(rootView);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPress(v);
            }
        });
    }

    protected void setArguments(CharSequence instructions) {
        this.instructions = instructions;
    }

    protected abstract void onButtonPress(View v);

    protected TextView getInstructionsTextView(View rootView) {
        return (TextView) rootView.findViewById(R.id.instructions_text_view);
    }

    protected Button getButton(View rootView) {
        return (Button) rootView.findViewById(R.id.finish_instructions_button);
    }
}
