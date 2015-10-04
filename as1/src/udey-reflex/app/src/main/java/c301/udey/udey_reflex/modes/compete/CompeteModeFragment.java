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

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.RadioGroup;

import c301.udey.udey_reflex.MainActivity;
import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.modes.InstructionsFragment;

/**
 * Fragment showing the instructions for the compete (multi-player) mode.
 * Also loads the {@link CompeteModeActivity} when user requests.
 */
public class CompeteModeFragment extends InstructionsFragment {

    public static final String EXTRA_MESSAGE_NUMBER_PLAYERS = "numberOfPlayers";
    private int numberPlayers;
    private boolean areNumberOfPlayersSelected;

    /**
     * Creates an instance of {@link CompeteModeFragment}.
     *
     * @param context       The callers context.
     * @param sectionNumber The section number where this fragment will be placed.
     * @return The generated CompeteModeFragment.
     */
    public static CompeteModeFragment getInstance(Context context, int sectionNumber) {
        CompeteModeFragment fragment = new CompeteModeFragment();
        fragment.setArguments(context.getString(R.string.compete_instructions));
        fragment.attachSectionNumber(sectionNumber);
        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onSectionAttached((MainActivity) context);
    }

    /**
     * Pops up the 'get number of players' radio buttons.
     */
    @Override
    public void onRefocus() {
        getNumberOfPlayers();
    }

    /**
     * The button press event handler that loads the {@link CompeteModeActivity}.
     * Corresponds the the state where user has read the instructions, and now wants to move on
     * to the game.
     *
     * @param v The button's view.
     */
    @Override
    protected void onButtonPress(View v) {
        if (areNumberOfPlayersSelected) {
            Intent intent = new Intent(getActivity(), CompeteModeActivity.class);
            intent.putExtra(EXTRA_MESSAGE_NUMBER_PLAYERS, numberPlayers);
            startActivity(intent);
        }
    }

    private void getNumberOfPlayers() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.compete_player_count_dialog);
        dialog.setTitle("Number of players:");
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ((MainActivity) getContext()).openDrawer();
            }
        });

        RadioGroup playerNumberChoices = (RadioGroup) dialog.findViewById(R.id.player_choice_radio_group);

        playerNumberChoices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                areNumberOfPlayersSelected = true;
                dialog.hide();
                numberPlayers = group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())) + 2;
            }
        });
        dialog.show();
        areNumberOfPlayersSelected = false;
    }
}
