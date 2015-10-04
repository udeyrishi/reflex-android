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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivanrg.countdownanimation.CountDownAnimation;

import c301.udey.udey_reflex.R;

/**
 * A fragment for showing the countdown for the practice mode.
 * The animation is facilitated by the {@link com.ivanrg.countdownanimation.CountDownAnimation}.
 * Source: https://github.com/IvanRF/CountDownAnimation/blob/master/src/com/ivanrf/countdownanimation/CountDownAnimation.java
 */
public class PracticeModeCountdownFragment extends Fragment {

    private static final String ARG_COUNTDOWN_DURATION_SECONDS = "countdownDurationSeconds";

    private int countdownDurationSeconds;

    private OnCountdownFinishedListener countdownFinishedListener;

    /**
     * The default constructor
     */
    public PracticeModeCountdownFragment() { }

    /**
     * Creates an instance of {@link PracticeModeCountdownFragment}.
     * @param countdownDurationSeconds The duration of the countdown in seconds.
     * @return The generated PracticeModeCountdownFragment.
     */
    public static PracticeModeCountdownFragment newInstance(int countdownDurationSeconds) {
        PracticeModeCountdownFragment fragment = new PracticeModeCountdownFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COUNTDOWN_DURATION_SECONDS, countdownDurationSeconds);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            countdownDurationSeconds = getArguments().getInt(ARG_COUNTDOWN_DURATION_SECONDS);
        }
    }

    /**
     * Inflates the layout and starts the animation.
     * @param inflater           The LayoutInflater.
     * @param container          The ViewGroup container that will contain the inflated view.
     * @param savedInstanceState The saved instance's state.
     * @return The inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_practice_mode_countdown, container, false);

        CountDownAnimation animation =
                new CountDownAnimation((TextView) v.findViewById(R.id.practice_countdown_textview),
                        countdownDurationSeconds);
        animation.setCountDownListener(new CountDownAnimation.CountDownListener() {
            @Override
            public void onCountDownEnd(CountDownAnimation animation) {
                onCountdownFinished();
            }
        });
        animation.start();

        return v;
    }

    private void onCountdownFinished() {
        if (countdownFinishedListener != null) {
            countdownFinishedListener.onCountdownFinished();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            countdownFinishedListener = (OnCountdownFinishedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnCountdownFinishedListener");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDetach() {
        super.onDetach();
        countdownFinishedListener = null;
    }

    /**
     * An interface to be implemented by the class that wants to listen to the countdown finishing
     * event,
     */
    public interface OnCountdownFinishedListener {

        /**
         * Callback for the countdown finishing event.
         */
        void onCountdownFinished();
    }

}
