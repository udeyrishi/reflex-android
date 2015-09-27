package c301.udey.udey_reflex.modes.practice;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import at.markushi.ui.CircleButton;
import c301.udey.udey_reflex.R;

public class PracticeModeTapFragment extends Fragment {
    private int minDelayMilliSeconds;
    private int maxDelayMilliseconds;

    private static final String ARG_MIN_DELAY_MILLISECONDS = "minDelayMilliSeconds";
    private static final String ARG_MAX_DELAY_MILLISECONDS = "maxDelayMilliSeconds";

    private OnBuzzerTappedListener onBuzzerTappedListener;

    private Long buttonDisplayedTime;

    public static PracticeModeTapFragment newInstance(int minDelayMilliSeconds, int maxDelayMilliseconds) {
        PracticeModeTapFragment fragment = new PracticeModeTapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MIN_DELAY_MILLISECONDS, minDelayMilliSeconds);
        args.putInt(ARG_MAX_DELAY_MILLISECONDS, maxDelayMilliseconds);
        fragment.setArguments(args);
        return fragment;
    }

    public PracticeModeTapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            minDelayMilliSeconds = getArguments().getInt(ARG_MIN_DELAY_MILLISECONDS);
            maxDelayMilliseconds = getArguments().getInt(ARG_MAX_DELAY_MILLISECONDS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_practice_mode_tap, container, false);
        setInstructions(rootView, getString(R.string.practice_session_wait));
        final CircleButton button = (CircleButton) rootView.findViewById(R.id.practice_buzzer_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed();
            }
        });

        // Prefer Alpha over visibility in this case for registering early taps
        // Udey Source: http://stackoverflow.com/questions/10612740/will-touch-get-detected-while-a-view-is-in-invisible-state
        final float oldAlpha = button.getAlpha();
        button.setAlpha(0.2f);

        TimerTask task = new TimerTask() {

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

        int delay = getRandom(minDelayMilliSeconds, maxDelayMilliseconds);
        new Timer().schedule(task, delay);
        return rootView;
    }


    protected static int getRandom(int min, int max) {
        return new Random().nextInt(max-min) + min;
    }

    private void setInstructions(View v, String value) {
        TextView instructions = (TextView)v.findViewById(R.id.practice_session_instructions);
        if (!instructions.getText().equals(value)) {
            instructions.setText(value);
        }
    }

    private void onButtonPressed() {
        Long currentTime = SystemClock.elapsedRealtime();
        final Long delay;
        if (buttonDisplayedTime == null) {
            // Tapped too early
            delay = new Long(-1);
        } else {
            delay = currentTime  - buttonDisplayedTime;
        }
        buttonDisplayedTime = currentTime;

        if (onBuzzerTappedListener != null) {
            // Delay it to complete the circular button animation
            // Udey Source: http://stackoverflow.com/questions/4111905/how-do-you-have-the-code-pause-for-a-couple-of-seconds-in-android
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onBuzzerTappedListener.onBuzzerTapped(delay);
                }
            }, 100);

        }
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        onBuzzerTappedListener = null;
    }

    public interface OnBuzzerTappedListener {
        void onBuzzerTapped(Long delay);
    }

}
