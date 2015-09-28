package c301.udey.udey_reflex.modes;

import android.support.v4.app.Fragment;
import android.content.Context;

import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.modes.compete.CompeteModeFragment;
import c301.udey.udey_reflex.modes.practice.PracticeModeFragment;
import c301.udey.udey_reflex.modes.stats.StatsModeFragment;

/**
 * Created by rishi on 15-09-26.
 */
public class AppModesProvider {

    public static String[] getAppModes(Context context) {
        return new String[] {
                context.getString(R.string.section_name_practice),
                context.getString(R.string.section_name_compete),
                context.getString(R.string.section_name_stats)
        };
    }

    public static Fragment selectAppMode(Context context, int sectionNumber) {
        switch(sectionNumber) {
            case 0:
                return PracticeModeFragment.getInstance(context, sectionNumber);
            case 1:
                return CompeteModeFragment.getInstance(context, sectionNumber);
            case 2:
                return StatsModeFragment.getInstance(sectionNumber);
            default:
                throw new IllegalArgumentException("No app mode defined at this index.");
        }
    }
}
