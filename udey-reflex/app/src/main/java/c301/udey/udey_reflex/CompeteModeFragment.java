package c301.udey.udey_reflex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rishi on 15-09-26.
 */
public class CompeteModeFragment extends AppModeFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mode_compete, container, false);
        return rootView;
    }

    public static CompeteModeFragment getInstance(int sectionNumber) {
        CompeteModeFragment fragment = new CompeteModeFragment();
        fragment.attachSectionNumber(sectionNumber);
        return fragment;
    }
}
