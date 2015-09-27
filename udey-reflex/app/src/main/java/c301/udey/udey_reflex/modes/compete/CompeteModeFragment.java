package c301.udey.udey_reflex.modes.compete;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.RadialGradient;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import c301.udey.udey_reflex.modes.AppModeFragment;
import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.modes.practice.PracticeModeActivity;

/**
 * Created by rishi on 15-09-26.
 */
public class CompeteModeFragment extends AppModeFragment {

    public static final String EXTRA_MESSAGE_NUMBER_PLAYERS = "numberOfPlayers";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mode_compete, container, false);
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.compete_player_count_dialog);
        dialog.setTitle("Number of players:");
        dialog.setCancelable(false);

        RadioGroup playerNumberChoices = (RadioGroup)dialog.findViewById(R.id.player_choice_radio_group);

        playerNumberChoices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                dialog.hide();
                int numberPlayers = group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())) + 2;
                Intent intent = new Intent(getActivity(), CompeteActivity.class);
                intent.putExtra(EXTRA_MESSAGE_NUMBER_PLAYERS, numberPlayers);
                startActivity(intent);
            }
        });
        dialog.show();

        return rootView;
    }

    public static CompeteModeFragment getInstance(int sectionNumber) {
        CompeteModeFragment fragment = new CompeteModeFragment();
        fragment.attachSectionNumber(sectionNumber);
        return fragment;
    }
}
