package c301.udey.udey_reflex.modes.practice;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import at.markushi.ui.CircleButton;
import c301.udey.udey_reflex.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnBuzzerTappedListener} interface
 * to handle interaction events.
 * Use the {@link PracticeModeTapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PracticeModeTapFragment extends Fragment {
    private static final int MIN_DELAY_MILLISECONDS = 10;
    private static final int MAX_DELAY_MILLISECONDS = 2000;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnBuzzerTappedListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PracticeModeTapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PracticeModeTapFragment newInstance(String param1, String param2) {
        PracticeModeTapFragment fragment = new PracticeModeTapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PracticeModeTapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_practice_mode_tap, container, false);
        setInstructions(v, getString(R.string.practice_session_wait));
        final CircleButton button = (CircleButton) v.findViewById(R.id.practice_buzzer_button);

        final int oldVisibilty = button.getVisibility();
        button.setVisibility(View.INVISIBLE);

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // Needs to be run on UI thread, else CalledFromWrongThreadException
                // Udey Source: http://stackoverflow.com/questions/5161951/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setInstructions(v, getString(R.string.practice_session_go));
                        button.setVisibility(oldVisibilty);
                    }
                });

            }
        };

        int delay = getRandom(MIN_DELAY_MILLISECONDS, MAX_DELAY_MILLISECONDS);
        new Timer().schedule(task, delay);
        return v;
    }


    protected static int getRandom(int min, int max) {
        return new Random().nextInt(max-min) + min;
    }

    private void setInstructions(View v, String value) {
        TextView instructions = (TextView)v.findViewById(R.id.practice_session_instructions);
        if (!instructions.getText().equals(value)) {
            instructions.setText(value);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onBuzzerTapped(new Long(0));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnBuzzerTappedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnCountdownFinishedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnBuzzerTappedListener {
        // TODO: Update argument type and name
        public void onBuzzerTapped(Long delay);
    }

}
