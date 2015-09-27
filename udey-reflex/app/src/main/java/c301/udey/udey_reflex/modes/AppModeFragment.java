package c301.udey.udey_reflex.modes;

/**
 * Created by rishi on 15-09-26.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import c301.udey.udey_reflex.MainActivity;

/**
 * A abstract fragment for the different app modes.
 */
public abstract class AppModeFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    protected static final String ARG_SECTION_NUMBER = "section_number";

    protected void attachSectionNumber(int sectionNumber) {
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        setArguments(args);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
