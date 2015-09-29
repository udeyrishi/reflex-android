package c301.udey.udey_reflex.modes.compete;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.modes.AppModeFragment;

/**
 * Created by rishi on 15-09-26.
 */
public class CompeteModeFragment extends AppModeFragment {

    public static final String EXTRA_MESSAGE_NUMBER_PLAYERS = "numberOfPlayers";
    private int numberPlayers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        getNumberOfPlayers();
        return rootView;
    }

    public static CompeteModeFragment getInstance(Context context, int sectionNumber) {
        final CompeteModeFragment fragment = new CompeteModeFragment();

        fragment.prepareFragment(
                sectionNumber,
                context.getString(R.string.compete_instructions));

        return fragment;
    }

    private void getNumberOfPlayers() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.compete_player_count_dialog);
        dialog.setTitle("Number of players:");
        dialog.setCancelable(false);

        RadioGroup playerNumberChoices = (RadioGroup)dialog.findViewById(R.id.player_choice_radio_group);

        playerNumberChoices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                dialog.hide();
                numberPlayers = group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())) + 2;
            }
        });
        dialog.show();
    }

    @Override
    protected void onButtonPress(View v) {
        Intent intent = new Intent(getActivity(), CompeteModeActivity.class);
        intent.putExtra(EXTRA_MESSAGE_NUMBER_PLAYERS, numberPlayers);
        startActivity(intent);
    }
}
