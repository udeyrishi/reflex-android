package c301.udey.udey_reflex.modes.stats;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import c301.udey.udey_reflex.MainActivity;
import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.sectionmanager.FragmentAttacher;

/**
 * Created by rishi on 15-09-26.
 */
public class StatsModeFragment extends Fragment {

    private FragmentAttacher fragmentAttacher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragmentAttacher = new FragmentAttacher(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mode_stats, container, false);
        return rootView;
    }

    public static StatsModeFragment getInstance(int sectionNumber) {
        StatsModeFragment fragment = new StatsModeFragment();
        fragment.fragmentAttacher.attachSectionNumber(sectionNumber);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentAttacher.onSectionAttached((MainActivity)context);
    }
}
