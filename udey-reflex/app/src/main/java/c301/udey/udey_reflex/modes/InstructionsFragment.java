package c301.udey.udey_reflex.modes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import c301.udey.udey_reflex.R;

/**
 * Created by rishi on 15-09-27.
 */
public abstract class InstructionsFragment extends Fragment {

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
        TextView v = getInstructionsTextView(rootView);
        v.setText(instructions);
    }

    private void setButtonHandler(View rootView) {
        Button b = getButton(rootView);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPress(v);
            }
        });
    }

    protected void setInstructions(CharSequence instructions) {
        this.instructions = instructions;
    }

    protected abstract void onButtonPress(View v);

    protected TextView getInstructionsTextView(View rootView) {
        return (TextView) rootView.findViewById(R.id.instructions_text_view);
    }

    protected Button getButton(View rootView) {
        return (Button) rootView.findViewById(R.id.finish_instructions_button);
    }
}
