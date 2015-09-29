package c301.udey.udey_reflex.modes.practice;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import c301.udey.udey_reflex.R;

public class PracticeModeResultFragment extends Fragment {
    private static final String ARG_DELAY_MILLISECONDS = "delayMilliseconds";

    private Long delayMilliseconds;

    private OnResultDismissedListener mListener;

    public static PracticeModeResultFragment newInstance(Long delayMilliseconds) {
        PracticeModeResultFragment fragment = new PracticeModeResultFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_DELAY_MILLISECONDS, delayMilliseconds);
        fragment.setArguments(args);
        return fragment;
    }

    public PracticeModeResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            delayMilliseconds = getArguments().getLong(ARG_DELAY_MILLISECONDS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);

        setDelayResult(rootView);

        Button tryAgainButton = (Button) rootView.findViewById(R.id.finish_instructions_button);

        tryAgainButton.setText(R.string.practice_try_again_button_text);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed();
            }
        });

        return rootView;
    }

    private void setDelayResult(View rootView) {
        TextView resultBox = (TextView)rootView.findViewById(R.id.instructions_text_view);
        if (delayMilliseconds < 0) {
            resultBox.setText("Too soon!");
        }
        else {
            resultBox.setText("Response time:\n" + delayMilliseconds.toString() + " ms");
        }
    }

    private void onButtonPressed() {
        if (mListener != null) {
            mListener.onTryAgain();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnResultDismissedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnResultDismissedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnResultDismissedListener {
        void onTryAgain();
    }
}
