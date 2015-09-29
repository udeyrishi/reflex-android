package c301.udey.udey_reflex.modes.practice;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.modes.InstructionsFragment;

public class PracticeModeResultFragment extends InstructionsFragment {

    private OnResultDismissedListener onResultDismissedListener;

    public static PracticeModeResultFragment newInstance(Long delayMilliseconds) {
        PracticeModeResultFragment fragment = new PracticeModeResultFragment();
        fragment.setInstructions(getResult(delayMilliseconds));
        return fragment;
    }

    private static CharSequence getResult(Long delayMilliseconds) {
        if (delayMilliseconds < 0) {
            return "Too soon!";
        }
        else {
            return "Response time:\n" + delayMilliseconds.toString() + " ms";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        getButton(rootView).setText(R.string.practice_try_again_button_text);
        return rootView;
    }

    @Override
    protected void onButtonPress(View v) {
        if (onResultDismissedListener != null) {
            onResultDismissedListener.onTryAgain();
        }
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        onResultDismissedListener = null;
    }

    public interface OnResultDismissedListener {
        void onTryAgain();
    }
}
