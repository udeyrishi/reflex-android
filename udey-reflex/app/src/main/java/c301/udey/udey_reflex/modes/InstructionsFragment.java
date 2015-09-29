package c301.udey.udey_reflex.modes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import c301.udey.udey_reflex.R;

/**
 * Created by rishi on 15-09-27.
 */
public abstract class InstructionsFragment extends AppModeFragment {

    private CharSequence instructions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);
        setButtonHandler(rootView);
        setInstructions(rootView);
        return rootView;
    }

    private void setInstructions(View rootView) {
        TextView v = (TextView) rootView.findViewById(R.id.instructions_text_view);
        v.setText(instructions);
    }

    private void setButtonHandler(View rootView) {
        Button b = (Button) rootView.findViewById(R.id.finish_instructions_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPress(v);
            }
        });
    }

    protected void prepareFragment(int sectionNumber, CharSequence instructions) {
        this.instructions = instructions;
        attachSectionNumber(sectionNumber);
    }

    protected abstract void onButtonPress(View v);
}
