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
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import c301.udey.udey_reflex.MainActivity;
import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.modes.InstructionsFragment;
import c301.udey.udey_reflex.sectionmanager.FragmentAttacher;

/**
 * Created by rishi on 15-09-26.
 */
public class CompeteModeFragment extends InstructionsFragment {

    public static final String EXTRA_MESSAGE_NUMBER_PLAYERS = "numberOfPlayers";
    private int numberPlayers;
    private FragmentAttacher fragmentAttacher;
    private boolean areNumberOfPlayersSelected;

    public CompeteModeFragment() {
        fragmentAttacher = new FragmentAttacher(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (fragmentAttacher == null) {
            fragmentAttacher = new FragmentAttacher(this);
        }
    }

    public static CompeteModeFragment getInstance(Context context, int sectionNumber) {
        CompeteModeFragment fragment = new CompeteModeFragment();
        fragment.setArguments(context.getString(R.string.compete_instructions));
        fragment.fragmentAttacher.attachSectionNumber(sectionNumber);
        return fragment;
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

        RadioGroup playerNumberChoices = (RadioGroup)dialog.findViewById(R.id.player_choice_radio_group);

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentAttacher.onSectionAttached((MainActivity) context);
    }

    @Override
    protected void onButtonPress(View v) {
        if (areNumberOfPlayersSelected) {
            Intent intent = new Intent(getActivity(), CompeteModeActivity.class);
            intent.putExtra(EXTRA_MESSAGE_NUMBER_PLAYERS, numberPlayers);
            startActivity(intent);
        }
    }

    @Override
    public void onRefocus() {
        getNumberOfPlayers();
    }
}
