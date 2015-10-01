package c301.udey.udey_reflex.modes.practice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import c301.udey.udey_reflex.MainActivity;
import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.modes.InstructionsFragment;
import c301.udey.udey_reflex.sectionmanager.FragmentAttacher;

/**
 * Created by rishi on 15-09-26.
 */
public class PracticeModeFragment extends InstructionsFragment {

    private FragmentAttacher fragmentAttacher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragmentAttacher = new FragmentAttacher(this);
    }

    public static PracticeModeFragment getInstance(Context context, int sectionNumber) {

        PracticeModeFragment fragment = new PracticeModeFragment();
        fragment.setInstructions(context.getString(R.string.practice_instructions));
        fragment.fragmentAttacher.attachSectionNumber(sectionNumber);
        return fragment;
    }

    @Override
    protected void onButtonPress(View v) {
        Intent intent = new Intent(getActivity(), PracticeModeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentAttacher.onSectionAttached((MainActivity) context);
    }
}
