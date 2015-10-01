package c301.udey.udey_reflex.modes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import c301.udey.udey_reflex.R;

public class ResultFragment extends InstructionsFragment {

    private OnResultDismissedListener onResultDismissedListener;

    public static ResultFragment newInstance(CharSequence result) {
        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(result);
        return fragment;
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
