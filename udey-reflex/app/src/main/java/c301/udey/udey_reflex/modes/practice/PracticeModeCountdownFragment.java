package c301.udey.udey_reflex.modes.practice;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ivanrg.countdownanimation.CountDownAnimation;

import c301.udey.udey_reflex.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnCountdownFinishedListener} interface
 * to handle interaction events.
 * Use the {@link PracticeModeCountdownFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PracticeModeCountdownFragment extends Fragment {

    private static final String ARG_COUNTDOWN_DURATION_SECONDS = "countdownDurationSeconds";

    private int countdownDurationSeconds;

    private OnCountdownFinishedListener countdownFinishedListener;

    public static PracticeModeCountdownFragment newInstance(int durationSeconds) {
        PracticeModeCountdownFragment fragment = new PracticeModeCountdownFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COUNTDOWN_DURATION_SECONDS, durationSeconds);
        fragment.setArguments(args);
        return fragment;
    }

    public PracticeModeCountdownFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            countdownDurationSeconds = getArguments().getInt(ARG_COUNTDOWN_DURATION_SECONDS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_practice_mode_countdown, container, false);

        // Udey Source: https://github.com/IvanRF/CountDownAnimation/blob/master/src/com/ivanrf/countdownanimation/CountDownAnimation.java
        CountDownAnimation animation =
                new CountDownAnimation((TextView)v.findViewById(R.id.practice_countdown_textview),
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

    @Override
    public void onDetach() {
        super.onDetach();
        countdownFinishedListener = null;
    }

    public interface OnCountdownFinishedListener {
        void onCountdownFinished();
    }

}
