/*
 * Copyright (C) 2015 Udey Rishi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package c301.udey.udey_reflex.modes.stats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import c301.udey.udey_reflex.RefocusAwareFragment;
import c301.udey.udey_reflex.sectionmanager.FragmentAttacher;
import c301.udey.udey_reflex.statisticsmanager.Statistic;
import c301.udey.udey_reflex.statisticsmanager.StatisticsManager;

/**
 * A fragment for showing the stats.
 */
public class StatsModeFragment extends RefocusAwareFragment {

    private FragmentAttacher fragmentAttacher;

    private ArrayList<Statistic<? extends Number>> reactionTimeStats;
    private ArrayList<Statistic<Long>> buzzerStats;

    private ArrayAdapter<Statistic<Long>> buzzerStatsAdapter;
    private ArrayAdapter<Statistic<? extends Number>> reactionTimeStatsAdapter;

    private ListView buzzerStatsBox;
    private ListView reactionTimeStatsBox;

    private StatisticsManager statsManager;

    /**
     * The default constructor.
     */
    public StatsModeFragment() {
        this.fragmentAttacher = new FragmentAttacher(this);
    }

    /**
     * Creates an instance of {@link StatsModeFragment}.
     * @param sectionNumber The section number where this fragment will be placed.
     * @return The generated StatsModeFragment.
     */
    public static StatsModeFragment getInstance(int sectionNumber) {
        StatsModeFragment fragment = new StatsModeFragment();
        fragment.fragmentAttacher.attachSectionNumber(sectionNumber);
        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (fragmentAttacher == null) {
            fragmentAttacher = new FragmentAttacher(this);
        }

        statsManager = new StatisticsManager(getContext());
        setHasOptionsMenu(true);
    }

    /**
     * Inflates the appropriate views, refreshes the stats, and displays them.
     * @param inflater           The LayoutInflater.
     * @param container          The ViewGroup container that will contain the inflated view.
     * @param savedInstanceState The saved instance's state.
     * @return The inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mode_stats, container, false);

        buzzerStatsBox = (ListView) rootView.findViewById(R.id.buzzer_count_stats_list);
        reactionTimeStatsBox = (ListView) rootView.findViewById(R.id.reaction_time_stats_list);

        refreshStats();
        return rootView;
    }

    /**
     * Adds the 'clear reaction time stats', 'clear buzzer count stats', and 'email stats'
     * options in the menu bar.
     * @param menu The menu.
     * @param inflater The menu inflator.
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentAttacher.onSectionAttached((MainActivity) context);
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
        for (Statistic<? extends Number> statistic : reactionTimeStats) {
            allStats.append(statistic.toString()).append("\n");
        }

        allStats.append("\n\nBuzzer count stats:\n");
        for (Statistic<Long> statistic : buzzerStats) {
            allStats.append(statistic.toString()).append("\n");
        }

        return allStats.toString();
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
