package c301.udey.udey_reflex.sectionmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by rishi on 15-10-01.
 */
public class FragmentAttacher {
    protected static final String ARG_SECTION_NUMBER = "section_number";

    private final Fragment fragment;

    public FragmentAttacher(Fragment fragment) {
        this.fragment = fragment;
    }

    public void attachSectionNumber(int sectionNumber) {
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
    }

    public void onSectionAttached(SectionHolder holder) {
        holder.onSectionAttached(fragment.getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
