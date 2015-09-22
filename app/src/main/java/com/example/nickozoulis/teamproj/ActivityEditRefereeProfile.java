package com.example.nickozoulis.teamproj;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nickozoulis.teamproj.domain.Area;
import com.example.nickozoulis.teamproj.domain.Council;
import com.example.nickozoulis.teamproj.domain.Referee;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ActivityEditRefereeProfile extends AppCompatActivity {

    private Referee referee;
    private int refereeIndex;
    private Toast toast;
    private String spinnerLevelSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_referee_profile);

        Bundle b = getIntent().getExtras();

        referee = new Referee((String)b.get(MainActivity.REFEREEINFO));
        refereeIndex = ((ArrayList)MainActivity.getReferees()).indexOf(referee);

        setupUI();
    }

    private void setupUI() {
        setupQualificationUI();
        setupHomeLocalitynUI();
        setupVisitLocalitynUI();
    }

    private void setupVisitLocalitynUI() {
        CheckBox checkBoxNorth = (CheckBox)findViewById(R.id.checkboxHomeNorth);
        CheckBox checkBoxCenter = (CheckBox)findViewById(R.id.checkboxHomeCenter);
        CheckBox checkBoxSouth = (CheckBox)findViewById(R.id.checkboxHomeSouth);

        if (referee.getLocality().getVisit().contains(Area.NORTH)) {
            checkBoxNorth.setChecked(true);
        }
        if (referee.getLocality().getVisit().contains(Area.CENTRAL)) {
            checkBoxCenter.setChecked(true);
        }
        if (referee.getLocality().getVisit().contains(Area.SOUTH)) {
            checkBoxSouth.setChecked(true);
        }
    }

    private void setupHomeLocalitynUI() {
        RadioButton radioButtonNorth = (RadioButton)findViewById(R.id.radioButtonHomeNorth);
        RadioButton radioButtonCenter = (RadioButton)findViewById(R.id.radioButtonHomeCenter);
        RadioButton radioButtonSouth = (RadioButton)findViewById(R.id.radioButtonHomeSouth);

        switch (referee.getLocality().getHome()) {
            case NORTH:
                radioButtonNorth.setChecked(true);
                break;
            case CENTRAL:
                radioButtonCenter.setChecked(true);
                break;
            case SOUTH:
                radioButtonSouth.setChecked(true);
                break;
            default:
        }
    }

    private void setupQualificationUI() {
        // Radio Buttons
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGrouQualif);
        RadioButton radioButtonNBJ = (RadioButton)findViewById(R.id.radioButtonNJB);
        RadioButton radioButtonIBJ = (RadioButton)findViewById(R.id.radioButtonIJB);

        if (referee.getQualification().getCouncil() == Council.NJB)
            radioButtonNBJ.setChecked(true);
        else if (referee.getQualification().getCouncil() == Council.IJB)
            radioButtonIBJ.setChecked(true);

        String[] range = {"0", "1", "2", "3", "4"};

        Spinner spinner = (Spinner)findViewById(R.id.spinnerQualifEditRefereeProfile);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, range); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new MySpinnerOnClickListener());
        spinner.setSelection(referee.getQualification().getLevel());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_edit_referee_profile, menu);
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
        } else if (id == R.id.saveProfileRefereeButton) {
            saveChangesAndQuit();
        } else if (id == R.id.deleteProfileRefereeButton) {
            deleteProfileAndQuit();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteProfileAndQuit() {
        ((ArrayList)MainActivity.getReferees()).remove(refereeIndex);

        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK - 1, resultIntent);
        finish();
    }

    private void saveChangesAndQuit() {
        if (isProfileValid()) {
            saveChanges();
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        } else {
            showToast("Home Area must be one of the Visit too.");
        }
    }

    private void saveChanges() {
        saveQualification();
        saveHomeLocality();
        saveVisitLocality();

        ((ArrayList)MainActivity.getReferees()).set(refereeIndex, referee);
    }

    private void saveVisitLocality() {
        CheckBox checkBoxNorth = (CheckBox)findViewById(R.id.checkboxHomeNorth);
        CheckBox checkBoxCenter = (CheckBox)findViewById(R.id.checkboxHomeCenter);
        CheckBox checkBoxSouth = (CheckBox)findViewById(R.id.checkboxHomeSouth);

        Set<Area> visit = new HashSet<Area>(3);

        if (checkBoxNorth.isChecked()) {
           visit.add(Area.NORTH);
        }
        if (checkBoxCenter.isChecked()) {
            visit.add(Area.CENTRAL);
        }
        if (checkBoxSouth.isChecked()) {
            visit.add(Area.SOUTH);
        }
        referee.getLocality().setVisit(visit);
    }

    private void saveHomeLocality() {
        int selected = ((RadioGroup)findViewById(R.id.radioGroupHomeLocality)).getCheckedRadioButtonId();

        if (selected == R.id.radioButtonHomeNorth) {
            referee.getLocality().setHome(Area.NORTH);
        } else if (selected == R.id.radioButtonHomeCenter) {
            referee.getLocality().setHome(Area.CENTRAL);
        } else if (selected == R.id.radioButtonHomeSouth) {
            referee.getLocality().setHome(Area.SOUTH);
        }
    }

    private void saveQualification() {
        int selected = ((RadioGroup)findViewById(R.id.radioGrouQualif)).getCheckedRadioButtonId();

        if (selected == R.id.radioButtonNJB) {
            referee.getQualification().setCouncil(Council.NJB);
        } else if (selected == R.id.radioButtonIJB) {
            referee.getQualification().setCouncil(Council.IJB);
        }

        referee.getQualification().setLevel(Integer.parseInt(spinnerLevelSelection));
    }

    public void showToast(String text) {
        if (toast != null) toast.cancel();
        toast = Toast.makeText(ActivityEditRefereeProfile.this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private boolean isProfileValid() {
        CheckBox checkBoxNorth = (CheckBox)findViewById(R.id.checkboxHomeNorth);
        CheckBox checkBoxCenter = (CheckBox)findViewById(R.id.checkboxHomeCenter);
        CheckBox checkBoxSouth = (CheckBox)findViewById(R.id.checkboxHomeSouth);

        int selected = ((RadioGroup)findViewById(R.id.radioGroupHomeLocality)).getCheckedRadioButtonId();

        if (selected == R.id.radioButtonHomeNorth) {
            if (!checkBoxNorth.isChecked())
                return false;
        } else if (selected == R.id.radioButtonHomeCenter) {
            if(!checkBoxCenter.isChecked())
            return false;
        } else if (selected == R.id.radioButtonHomeSouth) {
            if (!checkBoxSouth.isChecked())
                return false;
        }

        return true;
    }

    /**
     * A custom listener for the spinners. Is needed to get the selected values.
     */
    public class MySpinnerOnClickListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            spinnerLevelSelection = parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

}
