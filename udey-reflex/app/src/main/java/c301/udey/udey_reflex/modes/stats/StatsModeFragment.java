package c301.udey.udey_reflex.modes.stats;

import android.content.Context;
import android.content.Intent;
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

import c301.udey.udey_reflex.MainActivity;
import c301.udey.udey_reflex.R;
import c301.udey.udey_reflex.sectionmanager.FragmentAttacher;
import c301.udey.udey_reflex.statisticsmanager.Statistic;
import c301.udey.udey_reflex.statisticsmanager.StatisticsManager;

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

    private StatisticsManager statsManager;

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

        statsManager = new StatisticsManager(getContext());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mode_stats, container, false);

        buzzerStatsBox = (ListView)rootView.findViewById(R.id.buzzer_count_stats_list);
        reactionTimeStatsBox = (ListView)rootView.findViewById(R.id.reaction_time_stats_list);

        refreshStats();
        return rootView;
    }

    // Udey Source: http://stackoverflow.com/questions/8308695/android-options-menu-in-fragment
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem clearReactionTimeStatsItem = menu.add(R.string.clear_reaction_time_stats_label);
        clearReactionTimeStatsItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                clearReactionTimeStats();
                return true;
            }
        });

        MenuItem clearBuzzerStatsItem = menu.add(R.string.clear_buzzer_count_stats_label);
        clearBuzzerStatsItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                clearBuzzerStats();
                return true;
            }
        });

        MenuItem sendEmailItem = menu.add(R.string.send_email_stats_label);
        sendEmailItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                sendStatsEmail();
                return true;
            }
        });
    }

    private void sendStatsEmail() {
        // Udey Source: http://stackoverflow.com/questions/6583010/how-to-create-email-button-on-android
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.stats_email_subject));
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, getAllStatsSerialized());
        startActivityForResult(emailIntent, 1);
    }

    private String getAllStatsSerialized() {

        StringBuilder allStats = new StringBuilder("Reaction time stats:\n");
        for(Statistic<? extends Number> statistic : reactionTimeStats) {
            allStats.append(statistic.toString()).append("\n");
        }

        allStats.append("\n\nBuzzer count stats:\n");
        for(Statistic<Long> statistic : buzzerStats) {
            allStats.append(statistic.toString()).append("\n");
        }

        return allStats.toString();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentAttacher.onSectionAttached((MainActivity) context);
    }

    private void clearBuzzerStats() {
        statsManager.clearBuzzerCountStats();
        refreshBuzzerCountStats();
    }

    private void clearReactionTimeStats() {
        statsManager.clearReactionTimeStats();
        refreshReactionTimeStats();
    }

    private void refreshStats() {
        refreshBuzzerCountStats();
        refreshReactionTimeStats();
    }

    private void refreshReactionTimeStats() {
        reactionTimeStats = statsManager.getReactionTimeStats();
        reactionTimeStatsAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, reactionTimeStats);
        reactionTimeStatsBox.setAdapter(reactionTimeStatsAdapter);
    }

    private void refreshBuzzerCountStats() {
        buzzerStats = statsManager.getBuzzerCountStats();
        buzzerStatsAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, buzzerStats);
        buzzerStatsBox.setAdapter(buzzerStatsAdapter);
    }
}
