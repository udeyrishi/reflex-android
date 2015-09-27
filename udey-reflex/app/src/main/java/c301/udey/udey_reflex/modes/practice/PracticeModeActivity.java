package c301.udey.udey_reflex.modes.practice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import at.markushi.ui.CircleButton;
import c301.udey.udey_reflex.R;

public class PracticeModeActivity extends AppCompatActivity {

    private static final int MIN_DELAY_MILLISECONDS = 10;
    private static final int MAX_DELAY_MILLISECONDS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_mode);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setInstructions(getString(R.string.practice_session_wait));
        final CircleButton button = (CircleButton) findViewById(R.id.practice_buzzer_button);

        final int oldVisibilty = button.getVisibility();
        button.setVisibility(View.INVISIBLE);

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // Needs to be run on UI thread, else CalledFromWrongThreadException
                // Udey Source: http://stackoverflow.com/questions/5161951/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setInstructions(getString(R.string.practice_session_go));
                        button.setVisibility(oldVisibilty);
                    }
                });

            }
        };

        int delay = getRandom(MIN_DELAY_MILLISECONDS, MAX_DELAY_MILLISECONDS);
        new Timer().schedule(task, delay);
    }

    protected static int getRandom(int min, int max) {
        return new Random().nextInt(max-min) + min;
    }

    private void setInstructions(String value) {
        TextView instructions = (TextView)findViewById(R.id.practice_session_instructions);
        if (!instructions.getText().equals(value)) {
            instructions.setText(value);
        }
    }
}
