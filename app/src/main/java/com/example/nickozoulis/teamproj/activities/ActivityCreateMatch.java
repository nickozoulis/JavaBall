package com.example.nickozoulis.teamproj.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nickozoulis.teamproj.R;
import com.example.nickozoulis.teamproj.adapters.ListAdapterReferee;
import com.example.nickozoulis.teamproj.domain.Area;
import com.example.nickozoulis.teamproj.domain.Match;
import com.example.nickozoulis.teamproj.domain.Referee;
import com.example.nickozoulis.teamproj.util.algorithm.Allocation;
import com.example.nickozoulis.teamproj.util.algorithm.AllocationAlgorithm;

import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

public class ActivityCreateMatch extends AppCompatActivity {

    private int spinnerWeekSelection;
    private int radioGroupLevelSelection = -1;
    private Area radioGroupAreaSelection = null;

    private ListView listView;
    private ListAdapterReferee listAdapter;

    private Toast toast;

    private Allocation alloc;
    private Collection filteredCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);

        alloc = new AllocationAlgorithm();
        alloc.setCollection(MainActivity.getReferees());

        setupUI();
    }

    private void setupUI() {
        // ListView
        listView = (ListView)findViewById(R.id.suitableRefereesListView);

        // Spinner
        String[] weekRange = new String[52];
        for (int i=0; i<52; i++) {
            weekRange[i] = String.valueOf(i + 1);
        }

        Spinner spinnerWeek = (Spinner)findViewById(R.id.createMatchWeekNumberSpinner);
        ArrayAdapter<String> spinnerWeekArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weekRange);
        spinnerWeekArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeek.setAdapter(spinnerWeekArrayAdapter);
        spinnerWeek.setOnItemSelectedListener(new MySpinnerOnClickListener());
        spinnerWeek.setSelection(0);

        // Set RadioGroup Listeners
        ((RadioGroup)findViewById(R.id.createMatchRadioGroupArea)).setOnCheckedChangeListener(new MyRadioGroupAreaOnClickListener());
        ((RadioGroup)findViewById(R.id.createMatchRadioGroupLevel)).setOnCheckedChangeListener(new MyRadioGroupLevelOnClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_create_match, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.createMatchButton) {
            createMatchAndQuit();
        }

        return super.onOptionsItemSelected(item);
    }

    private void createMatchAndQuit() {
        if (filteredCollection == null || filteredCollection.size() < 2) {
            showToast("Not enough referees for a match!");
            return;
        }

        createMatch();

        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void createMatch() {
        //TODO: Check if week conflict
        Match m = new Match(spinnerWeekSelection, radioGroupLevelSelection, radioGroupAreaSelection);
        // The to first referees are the most suitable, collection has been sorted in algorithm.

        List suitableRefs = ((List)filteredCollection).subList(0,2);
        m.setReferees(suitableRefs);

        // Increment referees' allocated matches
        for (Referee r : (List<Referee>)suitableRefs) {
            r.setNumOfMatchAllocated(r.getNumOfMatchAllocated() + 1);
        }

        MainActivity.getMatches().add(m);
    }

    public void showToast(String text) {
        if (toast != null) toast.cancel();
        toast = Toast.makeText(ActivityCreateMatch.this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void refreshListView(Collection referees) {
        listAdapter = new ListAdapterReferee(this, R.layout.list_row, (List)referees);
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    /**
     * A custom listener for the spinners. Is needed to get the selected values.
     */
    private class MySpinnerOnClickListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            spinnerWeekSelection = pos + 1;
        }

        @Override
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    /**
     * A custom listener for the radio groups. Is needed to get the selected values.
     */
    private class MyRadioGroupLevelOnClickListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int selected = ((RadioGroup)findViewById(R.id.createMatchRadioGroupLevel)).getCheckedRadioButtonId();

            if (selected == R.id.createMatchRadioButtonJunior) {
                radioGroupLevelSelection = 1;
            } else if (selected == R.id.createMatchRadioButtonSenior) {
                radioGroupLevelSelection = 2;
            }

            filterAndRefresh(radioGroupLevelSelection);
        }
    }

    /**
     * A custom listener for the radio groups. Is needed to get the selected values.
     */
    private class MyRadioGroupAreaOnClickListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int selected = ((RadioGroup)findViewById(R.id.createMatchRadioGroupArea)).getCheckedRadioButtonId();

            if (selected == R.id.createMatchRadioButtonNorth) {
                radioGroupAreaSelection = Area.NORTH;
            } else if (selected == R.id.createMatchRadioButtonCentral) {
                radioGroupAreaSelection = Area.CENTRAL;
            } else if (selected == R.id.createMatchRadioButtonSouth) {
                radioGroupAreaSelection = Area.SOUTH;
            }

            filterAndRefresh(radioGroupAreaSelection);
        }
    }

    private void filterAndRefresh(int level) {
        filterAndRefresh(level, radioGroupAreaSelection);
    }

    private void filterAndRefresh(Area area) {
        filterAndRefresh(radioGroupLevelSelection, area);
    }

    private void filterAndRefresh(int level, Area area) {
        //TODO: Make it run even if an option is not selected.
        if (radioGroupLevelSelection == -1 || radioGroupAreaSelection == null) {
            showToast("Pick a selection for area And level!");
            return;
        }

        alloc.setLevel(level);
        alloc.setArea(area);

        //TODO: Make it thread to avoid UI crash
        filteredCollection = alloc.filter();
        refreshListView(filteredCollection);
    }

}
