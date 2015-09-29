package c301.udey.udey_reflex.modes.practice;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.modes.AppModeFragment;
import c301.udey.udey_reflex.modes.InstructionsFragment;

/**
 * Created by rishi on 15-09-26.
 */
public class PracticeModeFragment extends AppModeFragment {

    public static PracticeModeFragment getInstance(Context context, int sectionNumber) {

        PracticeModeFragment fragment = new PracticeModeFragment();
        fragment.prepareFragment(
                sectionNumber,
                context.getString(R.string.practice_instructions));
        return fragment;
    }

    @Override
    protected void onButtonPress(View v) {
        Intent intent = new Intent(getActivity(), PracticeModeActivity.class);
        startActivity(intent);
    }
}
