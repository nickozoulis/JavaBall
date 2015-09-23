package com.example.nickozoulis.teamproj;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ActivityCreateMatch extends AppCompatActivity {

    private Activity activityCreateMatch;
    private String spinnerWeekSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);

        activityCreateMatch = this;

        setupUI();
    }

    private void setupUI() {

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
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A custom listener for the spinners. Is needed to get the selected values.
     */
    private class MySpinnerOnClickListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            spinnerWeekSelection = parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }
}
