package c301.udey.udey_reflex;

/**
 * Created by rishi on 15-09-26.
 */
public class CompeteModeFragment extends AppModeFragment {

    public static CompeteModeFragment getInstance(int sectionNumber) {
        CompeteModeFragment fragment = new CompeteModeFragment();
        fragment.attachSectionNumber(sectionNumber);
        return fragment;
    }
}
