package c301.udey.udey_reflex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by rishi on 15-09-26.
 */
public class PracticeModeFragment extends AppModeFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mode_practice, container, false);
        return rootView;
    }

    public static PracticeModeFragment getInstance(int sectionNumber) {
        PracticeModeFragment fragment = new PracticeModeFragment();
        fragment.attachSectionNumber(sectionNumber);
        return fragment;
    }
}
