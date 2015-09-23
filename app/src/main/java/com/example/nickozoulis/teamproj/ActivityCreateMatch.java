package com.example.nickozoulis.teamproj;

import android.app.Activity;
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

import com.example.nickozoulis.teamproj.adapters.ListAdapterReferee;
import com.example.nickozoulis.teamproj.domain.Area;

import java.util.Collection;
import java.util.List;

public class ActivityCreateMatch extends AppCompatActivity {

    private String spinnerWeekSelection;

    private ListView listView;
    private ListAdapterReferee listAdapter;

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);


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

    private Collection softFilter(int weekNumber, int level, Area area) {

    }

    private Collection hardFilter(int weekNumber, int level, Area area) {

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
            spinnerWeekSelection = parent.getItemAtPosition(pos).toString();

            refreshListView(MainActivity.getReferees());
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

            } else if (selected == R.id.createMatchRadioButtonSenior) {

            }

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

            } else if (selected == R.id.createMatchRadioButtonCentral) {

            } else if (selected == R.id.createMatchRadioButtonSouth) {

            }

        }
    }

}
