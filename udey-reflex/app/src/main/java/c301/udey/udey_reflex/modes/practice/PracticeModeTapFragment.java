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
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import at.markushi.ui.CircleButton;
import c301.udey.udey_reflex.R;

/**
 * A fragment containing the buzzer buttons for the practice (single-player) mode.
 */
public class PracticeModeTapFragment extends Fragment {
    private static final String ARG_MIN_DELAY_MILLISECONDS = "minDelayMilliSeconds";
    private static final String ARG_MAX_DELAY_MILLISECONDS = "maxDelayMilliSeconds";
    private int minDelayMilliSeconds;
    private int maxDelayMilliseconds;
    private OnBuzzerTappedListener onBuzzerTappedListener;

    private Long buttonDisplayedTime;
    private Timer timer;

    /**
     * Default constructor.
     */
    public PracticeModeTapFragment() { }

    /**
     * Creates a new instance of {@link PracticeModeTapFragment}
     * @param minDelayMilliSeconds The minimum delay value in milliseconds before the buzzer is to be
     *                             activated.
     * @param maxDelayMilliseconds The maximum delay value in milliseconds before the buzzer is to be
     *                             activated.
     * @return The created PracticeModeTapFragment.
     */
    public static PracticeModeTapFragment newInstance(int minDelayMilliSeconds, int maxDelayMilliseconds) {
        PracticeModeTapFragment fragment = new PracticeModeTapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MIN_DELAY_MILLISECONDS, minDelayMilliSeconds);
        args.putInt(ARG_MAX_DELAY_MILLISECONDS, maxDelayMilliseconds);
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
            minDelayMilliSeconds = getArguments().getInt(ARG_MIN_DELAY_MILLISECONDS);
            maxDelayMilliseconds = getArguments().getInt(ARG_MAX_DELAY_MILLISECONDS);
        }
        timer = new Timer();
    }

    /**
     * Inflates the buzzer button view, and configures the button to be activated after a random
     * delay.
     * @param inflater           The LayoutInflater.
     * @param container          The ViewGroup container that will contain the inflated view.
     * @param savedInstanceState The saved instance's state.
     * @return The inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_practice_mode_tap, container, false);

        setInstructions(rootView, getString(R.string.practice_session_wait));

        CircleButton button = (CircleButton) rootView.findViewById(R.id.practice_buzzer_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed();
            }
        });
        TimerTask alphaChangeTask = configureBuzzerActivationTask(rootView, button);

        int delay = getRandom(minDelayMilliSeconds, maxDelayMilliseconds);
        timer.schedule(alphaChangeTask, delay);

        return rootView;
    }

    /**
     * Sets the button alpha to a translucent value, and creates a task to restore it to the original
     * value. Also changes the instructions to {@link c301.udey.udey_reflex.R.string#practice_session_go}.
     * @param rootView The root View.
     * @param button The buzzer button.
     * @return The created task.
     */
    @NonNull
    private TimerTask configureBuzzerActivationTask(final View rootView, final CircleButton button) {
        // Prefer Alpha over visibility in this case for registering early taps
        // Udey Source: http://stackoverflow.com/questions/10612740/will-touch-get-detected-while-a-view-is-in-invisible-state
        final float oldAlpha = button.getAlpha();
        button.setAlpha(0.2f);

        return new TimerTask() {

            @Override
            public void run() {
                // Needs to be run on UI thread, else CalledFromWrongThreadException
                // Udey Source: http://stackoverflow.com/questions/5161951/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
                // Activity null check for the cases where back button might be pressed, or buzzer
                // is pressed too soon
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setInstructions(rootView, getString(R.string.practice_session_go));
                            button.setAlpha(oldAlpha);
                            buttonDisplayedTime = SystemClock.elapsedRealtime();
                        }
                    });
                }
            }
        };
    }

    private void setInstructions(View v, String value) {
        TextView instructions = (TextView) v.findViewById(R.id.practice_session_instructions);
        if (!instructions.getText().equals(value)) {
            instructions.setText(value);
        }
    }

    /**
     * The buzzer tapped callback.
     */
    private void onButtonPressed() {
        timer.cancel();
        Long currentTime = SystemClock.elapsedRealtime();

        final Long delayMilliseconds;
        if (buttonDisplayedTime == null) {
            // Tapped too early
            delayMilliseconds = new Long(-1);
        }
        else {
            delayMilliseconds = currentTime - buttonDisplayedTime;
            buttonDisplayedTime = null;
        }

        if (onBuzzerTappedListener != null) {
            // Delay it to complete the circular button animation
            // Udey Source: http://stackoverflow.com/questions/4111905/how-do-you-have-the-code-pause-for-a-couple-of-seconds-in-android
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onBuzzerTappedListener.onBuzzerTapped(delayMilliseconds);
                }
            }, 100);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onBuzzerTappedListener = (OnBuzzerTappedListener) context;
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
        onBuzzerTappedListener = null;
    }

    /**
     * An interface to be implemented by the class interested in listening to the buzzer tap event.
     */
    public interface OnBuzzerTappedListener {
        /**
         * The callback for the buzzer tapping event.
         * @param delayInMilliseconds The delayInMilliseconds in milliseconds that the user took to hit the buzzer.
         */
        void onBuzzerTapped(Long delayInMilliseconds);
    }

    private static int getRandom(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }
}
