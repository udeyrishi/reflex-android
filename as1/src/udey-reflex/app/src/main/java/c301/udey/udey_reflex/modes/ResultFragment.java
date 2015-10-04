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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import c301.udey.udey_reflex.R;

/**
 * A {@link InstructionsFragment} for displaying the result of an app mode and a 'Try again' button.
 * Useful when the button press's logic resides in an activity. In that case, the activity should implement
 * {@link c301.udey.udey_reflex.modes.ResultFragment.OnResultDismissedListener}
 */
public class ResultFragment extends InstructionsFragment {

    private OnResultDismissedListener onResultDismissedListener;

    /**
     * Creates a new instance of {@link ResultFragment}.
     *
     * @param result The result to be displayed.
     * @return The created ResultFragment.
     */
    public static ResultFragment newInstance(CharSequence result) {
        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(result);
        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        getButton().setText(R.string.practice_try_again_button_text);
        return rootView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onButtonPress(View v) {
        if (onResultDismissedListener != null) {
            onResultDismissedListener.onResultDismissed();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onResultDismissedListener = (OnResultDismissedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnResultDismissedListener");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDetach() {
        super.onDetach();
        onResultDismissedListener = null;
    }

    /**
     * An interface to be implemented by a class interested in listening to the result dismissal
     * event.
     */
    public interface OnResultDismissedListener {
        /**
         * Callback for the result dismissal event.
         */
        void onResultDismissed();
    }
}
