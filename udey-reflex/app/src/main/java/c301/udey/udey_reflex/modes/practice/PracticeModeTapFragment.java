package c301.udey.udey_reflex.modes.practice;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        final int oldVisibilty = button.getVisibility();
        button.setVisibility(View.INVISIBLE);

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // Needs to be run on UI thread, else CalledFromWrongThreadException
                // Udey Source: http://stackoverflow.com/questions/5161951/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setInstructions(rootView, getString(R.string.practice_session_go));
                        button.setVisibility(oldVisibilty);
                    }
                });

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

    private void onButtonPressed(Uri uri) {
        if (onBuzzerTappedListener != null) {
            onBuzzerTappedListener.onBuzzerTapped(new Long(0));
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
        public void onBuzzerTapped(Long delay);
    }

}
