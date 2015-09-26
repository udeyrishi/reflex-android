package c301.udey.udey_reflex;

import android.content.Context;

import java.security.PrivilegedActionException;
import java.util.ArrayList;

/**
 * Created by rishi on 15-09-26.
 */
public class AppModesProvider {

    private static AppModesProvider instance = new AppModesProvider();

    public static AppModesProvider getInstance() {
        return instance;
    }

    private AppModesProvider() { }

    public String[] getAppModes(Context context) {
        return new String[] {
                context.getString(R.string.section_name_practice),
                context.getString(R.string.section_name_compete),
                context.getString(R.string.section_name_stats)
        };
    }

    public AppModeFragment selectAppMode(int sectionNumber) {
        AppModeFragment fragment;

        switch(sectionNumber) {
            case 0:
                return PracticeModeFragment.getInstance(sectionNumber);
            case 1:
                return CompeteModeFragment.getInstance(sectionNumber);
            case 2:
                return StatsModeFragment.getInstance(sectionNumber);
            default:
                throw new IllegalArgumentException("No app mode defined at this index.");
        }
    }
}
