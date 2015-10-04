/*
 * Copyright (C) 2015 Udey Rishi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package c301.udey.udey_reflex.modes.compete;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;
import c301.udey.udey_reflex.R;

/**
 * A fragment containing the buzzer buttons for the compete (multi-player) mode.
 */
public class CompeteModeTapFragment extends Fragment {
    private static final String ARG_NUMBER_OF_PLAYERS = "numberOfPlayers";

    private int numberOfPlayers;

    private OnBuzzerTappedListener buzzerTappedListener;

    /**
     * Required empty public constructor
     */
    public CompeteModeTapFragment() {
    }

    /**
     * Creates an instance of the {@link CompeteModeTapFragment}.
     *
     * @param numberOfPlayers The number of players competing.
     * @return The created CompeteModeTapFragment.
     */
    public static CompeteModeTapFragment newInstance(int numberOfPlayers) {
        CompeteModeTapFragment fragment = new CompeteModeTapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NUMBER_OF_PLAYERS, numberOfPlayers);
        fragment.setArguments(args);
        return fragment;
    }

    private static Pair<CharSequence, CircleButton> getBuzzerAndItsDescription(View rootView,
                                                                               int textViewId,
                                                                               int buzzerId) {
        CharSequence description = ((TextView) rootView.findViewById(textViewId)).getText();
        CircleButton button = (CircleButton) rootView.findViewById(buzzerId);
        return new Pair<>(description, button);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            numberOfPlayers = getArguments().getInt(ARG_NUMBER_OF_PLAYERS);
        }
    }

    /**
     * Inflates the appropriate view based on the number of players playing.
     *
     * @param inflater           The LayoutInflater.
     * @param container          The ViewGroup container that will contain the inflated view.
     * @param savedInstanceState The saved instance's state.
     * @return The inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflateProperLayout(inflater, container);
        ArrayList<Pair<CharSequence, CircleButton>> buttons = getBuzzerButtons(rootView);
        registerBuzzerCallbacks(buttons);
        return rootView;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDetach() {
        super.onDetach();
        buzzerTappedListener = null;
    }

    /**
     * Since all the buzzer buttons are symmetrical, this method registers callbacks with the same logic
     * to all the buttons. The CharSequence (i.e., label) corresponding to these buttons should be used
     * to identify the source of the callback.
     *
     * @param buttons The list of buttons.
     */
    private void registerBuzzerCallbacks(List<Pair<CharSequence, CircleButton>> buttons) {
        for (final Pair<CharSequence, CircleButton> button : buttons) {
            button.second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonPressed(button.first);
                }
            });
        }
    }

    private void onButtonPressed(final CharSequence playerWhoWon) {
        if (buzzerTappedListener != null) {
            // For completing the circular animation
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    buzzerTappedListener.onBuzzerTapped(playerWhoWon);
                }
            }, 100);
        }
    }

    private View inflateProperLayout(LayoutInflater inflater, ViewGroup container) {
        switch (numberOfPlayers) {
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

    private ArrayList<Pair<CharSequence, CircleButton>> getBuzzerButtons(View rootView) {
        ArrayList<Pair<CharSequence, CircleButton>> buttons = new ArrayList<>();
        switch (numberOfPlayers) {
            case 2:
                buttons.add(getBuzzerAndItsDescription(rootView, R.id.two_player_player_one, R.id.two_player_buzzer_one));
                buttons.add(getBuzzerAndItsDescription(rootView, R.id.two_player_player_two, R.id.two_player_buzzer_two));
                break;
            case 3:
                buttons.add(getBuzzerAndItsDescription(rootView, R.id.three_player_player_one, R.id.three_player_buzzer_one));
                buttons.add(getBuzzerAndItsDescription(rootView, R.id.three_player_player_two, R.id.three_player_buzzer_two));
                buttons.add(getBuzzerAndItsDescription(rootView, R.id.three_player_player_three, R.id.three_player_buzzer_three));
                break;
            case 4:
                buttons.add(getBuzzerAndItsDescription(rootView, R.id.four_player_player_one, R.id.four_player_buzzer_one));
                buttons.add(getBuzzerAndItsDescription(rootView, R.id.four_player_player_two, R.id.four_player_buzzer_two));
                buttons.add(getBuzzerAndItsDescription(rootView, R.id.four_player_player_three, R.id.four_player_buzzer_three));
                buttons.add(getBuzzerAndItsDescription(rootView, R.id.four_player_player_four, R.id.four_player_buzzer_four));
                break;
            default:
                throw new UnsupportedOperationException("numberOfPlayers can only be between 2-4.");
        }
        return buttons;
    }

    /**
     * An interface to be implemented by the class that wants to listen the buzzer's tap event
     */
    public interface OnBuzzerTappedListener {
        /**
         * Callback for the buzzer tap event.
         * @param playerWhoWon The player's name who won.
         */
        void onBuzzerTapped(CharSequence playerWhoWon);
    }
}
