package c301.udey.udey_reflex.modes.compete;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import c301.udey.udey_reflex.R;

public class CompeteModeTapFragment extends Fragment {
    private static final String ARG_NUMBER_OF_PLAYERS = "numberOfPlayers";

    private int numberOfPlayers;

    private OnBuzzerTappedListener buzzerTappedListener;

    public static CompeteModeTapFragment newInstance(int numberOfPlayers) {
        CompeteModeTapFragment fragment = new CompeteModeTapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NUMBER_OF_PLAYERS, numberOfPlayers);
        fragment.setArguments(args);
        return fragment;
    }

    public CompeteModeTapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            numberOfPlayers = getArguments().getInt(ARG_NUMBER_OF_PLAYERS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflateProperLayout(inflater, container);
        return rootView;
    }

    private View inflateProperLayout(LayoutInflater inflater, ViewGroup container) {
        // TODO: Consider refactoring this to be auto generated based on number of players
        // Potentially reusable with practice mode
        switch(numberOfPlayers) {
            case 2:
                return inflater.inflate(R.layout.fragment_compete_two_player, container, false);
            case 3:
                return inflater.inflate(R.layout.fragment_compete_three_player, container, false);
            case 4:
                return inflater.inflate(R.layout.fragment_compete_four_player, container, false);
            default:
                throw new UnsupportedOperationException("numberOfPlayers can only be between 2-4.");
        }
    }

    public void onButtonPressed(int playerNumberWhoWon) {
        if (buzzerTappedListener != null) {
            buzzerTappedListener.onBuzzerTapped(playerNumberWhoWon);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            buzzerTappedListener = (OnBuzzerTappedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnBuzzerTappedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        buzzerTappedListener = null;
    }


    public interface OnBuzzerTappedListener {
        void onBuzzerTapped(int playerNumberWhoWon);
    }

}
