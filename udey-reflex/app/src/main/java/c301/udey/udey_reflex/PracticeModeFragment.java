package c301.udey.udey_reflex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by rishi on 15-09-26.
 */
public class PracticeModeFragment extends AppModeFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mode_practice, container, false);
        Button b = (Button) rootView.findViewById(R.id.begin_practice_button);
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBegin(v);
            }
        });
        return rootView;
    }

    public static PracticeModeFragment getInstance(int sectionNumber) {
        PracticeModeFragment fragment = new PracticeModeFragment();
        fragment.attachSectionNumber(sectionNumber);
        return fragment;
    }

    public void onBegin(View view) {
        Toast.makeText(getActivity(), "Begin pressed.", Toast.LENGTH_SHORT).show();
    }
}
