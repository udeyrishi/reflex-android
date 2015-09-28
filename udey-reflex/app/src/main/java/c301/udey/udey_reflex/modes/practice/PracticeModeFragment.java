package c301.udey.udey_reflex.modes.practice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import c301.udey.udey_reflex.modes.AppModeFragment;
import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.modes.InstructionsFragment;

/**
 * Created by rishi on 15-09-26.
 */
public class PracticeModeFragment extends InstructionsFragment {

    public static PracticeModeFragment getInstance(Context context, int sectionNumber) {

        PracticeModeFragment fragment = new PracticeModeFragment();
        fragment.prepareFragment(
                sectionNumber,
                PracticeModeActivity.class,
                new IntentPreparer() {
                    @Override
                    public void prepareIntent(Intent intent) {
                        // Nothing special needed
                    }
                },
                context.getString(R.string.practice_instructions));
        return fragment;
    }
}
