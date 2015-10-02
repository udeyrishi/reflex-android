package c301.udey.udey_reflex.modes.stats;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import c301.udey.udey_reflex.Constants;
import c301.udey.udey_reflex.MainActivity;
import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.filestorage.CachedFileStorageManager;
import c301.udey.udey_reflex.filestorage.LocalFileStorageManager;
import c301.udey.udey_reflex.sectionmanager.FragmentAttacher;
import c301.udey.udey_reflex.statisticsmanager.BuzzerCountStatisticsManager;
import c301.udey.udey_reflex.statisticsmanager.ReactionTimeStatisticsManager;
import c301.udey.udey_reflex.statisticsmanager.Statistic;

/**
 * Created by rishi on 15-09-26.
 */
public class StatsModeFragment extends Fragment {

    private FragmentAttacher fragmentAttacher;

    private ArrayList<Statistic<? extends Number>> reactionTimeStats;
    private ArrayList<Statistic<Long>> buzzerStats;

    private ArrayAdapter<Statistic<Long>> buzzerStatsAdapter;
    private ArrayAdapter<Statistic<? extends Number>> reactionTimeStatsAdapter;

    private ListView buzzerStatsBox;
    private ListView reactionTimeStatsBox;

    private BuzzerCountStatisticsManager buzzerCountStatisticsManager;
    private ReactionTimeStatisticsManager reactionTimeStatisticsManager;

    public StatsModeFragment() {
        this.fragmentAttacher = new FragmentAttacher(this);
    }

    public static StatsModeFragment getInstance(int sectionNumber) {
        StatsModeFragment fragment = new StatsModeFragment();
        fragment.fragmentAttacher.attachSectionNumber(sectionNumber);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (fragmentAttacher == null) {
            fragmentAttacher = new FragmentAttacher(this);
        }

        buzzerCountStatisticsManager
                = new BuzzerCountStatisticsManager(new CachedFileStorageManager(new LocalFileStorageManager(getContext())),
                Constants.BUZZER_STATS_FILE_NAME);
        reactionTimeStatisticsManager
                = new ReactionTimeStatisticsManager(
                new CachedFileStorageManager(new LocalFileStorageManager(getContext())),
                Constants.REACTION_STATS_FILE_NAME);

        reactionTimeStats = getReactionTimeStats();
        buzzerStats = getBuzzerTimeStats();

        buzzerStatsAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, buzzerStats);
        reactionTimeStatsAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, reactionTimeStats);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mode_stats, container, false);

        buzzerStatsBox = (ListView)rootView.findViewById(R.id.buzzerStatsList);
        reactionTimeStatsBox = (ListView)rootView.findViewById(R.id.reactionTimeStatsList);

        buzzerStatsBox.setAdapter(buzzerStatsAdapter);
        reactionTimeStatsBox.setAdapter(reactionTimeStatsAdapter);
        return rootView;
    }

    // Udey Source: http://stackoverflow.com/questions/8308695/android-options-menu-in-fragment
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem clearReactionTimeStatsItem = menu.add(R.string.clear_reaction_time_stats_label);
        clearReactionTimeStatsItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                clearReactionTimeStats();
                return true;
            }
        });

        final MenuItem clearBuzzerStatsItem = menu.add(R.string.clear_buzzer_count_stats_label);
        clearBuzzerStatsItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                clearBuzzerStats();
                return true;
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentAttacher.onSectionAttached((MainActivity) context);
    }

    private void clearBuzzerStats() {
        buzzerCountStatisticsManager.clearStats();
        refreshStats();
    }

    private void clearReactionTimeStats() {
        reactionTimeStatisticsManager.clearStats();
        refreshStats();
    }

    private void refreshStats() {

    }

    private ArrayList<Statistic<Long>> getBuzzerTimeStats() {
        ArrayList<Statistic<Long>> buzzerTimeStats = new ArrayList<>();

        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(2,
                getContext().getString(R.string.player_one)));
        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(2,
                getContext().getString(R.string.player_two)));

        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(3,
                getContext().getString(R.string.player_one)));
        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(3,
                getContext().getString(R.string.player_two)));
        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(3,
                getContext().getString(R.string.player_three)));

        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(4,
                getContext().getString(R.string.player_one)));
        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(4,
                getContext().getString(R.string.player_two)));
        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(4,
                getContext().getString(R.string.player_three)));
        buzzerTimeStats.add(buzzerCountStatisticsManager.getNumberOfBuzzes(4,
                getContext().getString(R.string.player_four)));

        return buzzerTimeStats;
    }

    private ArrayList<Statistic<? extends Number>> getReactionTimeStats() {
        ArrayList<Statistic<? extends Number>> reactionTimeStats = new ArrayList<>();

        reactionTimeStats.add(reactionTimeStatisticsManager.getMinimumTime(10));
        reactionTimeStats.add(reactionTimeStatisticsManager.getMinimumTime(100));

        reactionTimeStats.add(reactionTimeStatisticsManager.getMaximumTime(10));
        reactionTimeStats.add(reactionTimeStatisticsManager.getMaximumTime(100));

        reactionTimeStats.add(reactionTimeStatisticsManager.getAverageTime(10));
        reactionTimeStats.add(reactionTimeStatisticsManager.getAverageTime(100));

        reactionTimeStats.add(reactionTimeStatisticsManager.getMedianTime(10));
        reactionTimeStats.add(reactionTimeStatisticsManager.getMedianTime(100));

        return reactionTimeStats;
    }
}
