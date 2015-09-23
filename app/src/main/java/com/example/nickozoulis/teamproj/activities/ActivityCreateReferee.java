package com.example.nickozoulis.teamproj.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nickozoulis.teamproj.R;
import com.example.nickozoulis.teamproj.domain.Area;
import com.example.nickozoulis.teamproj.domain.Council;
import com.example.nickozoulis.teamproj.domain.Person;
import com.example.nickozoulis.teamproj.domain.Referee;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ActivityCreateReferee extends AppCompatActivity {

    private Toast toast;
    private Referee referee;
    private String spinnerLevelSelection, spinnerMatchesSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_referee);

        referee = new Referee();

        setupUI();
    }

    /*
        Puts some filters in both EditTexts to only allow alphabetic characters.
     */
    private void setupUI() {
        EditText editTextFirstName = (EditText)findViewById(R.id.createRefereeFirstNameEditText);
        EditText editTextLastName = (EditText)findViewById(R.id.createRefereeLastNameEditText);

        editTextFirstName.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) { // for backspace
                            return src;
                        }
                        if (src.toString().matches("[a-zA-Z ]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });

        editTextLastName.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) { // for backspace
                            return src;
                        }
                        if (src.toString().matches("[a-zA-Z ]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });

        String[] rangeLevel = {"0", "1", "2", "3", "4"};

        Spinner spinnerLevel = (Spinner)findViewById(R.id.createRefereeSpinnerQualif);
        ArrayAdapter<String> spinnerLevelArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rangeLevel);
        spinnerLevelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevel.setAdapter(spinnerLevelArrayAdapter);
        spinnerLevel.setOnItemSelectedListener(new MySpinnerOnClickListener(0));
        spinnerLevel.setSelection(0);

        String[] rangeMatches = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

        Spinner spinnerMatches = (Spinner)findViewById(R.id.createRefereeSpinnerMatches);
        ArrayAdapter<String> spinnerMatchesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rangeMatches);
        spinnerMatchesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMatches.setAdapter(spinnerMatchesArrayAdapter);
        spinnerMatches.setOnItemSelectedListener(new MySpinnerOnClickListener(1));
        spinnerMatches.setSelection(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_create_referee, menu);
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
        } else if (id == R.id.createRefereeSaveButton) {
            saveChangesAndQuit();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveChangesAndQuit() {
        if (isProfileValid()) {
            saveChanges();
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }

    private void saveChanges() {
        saveFullName();
        saveQualification();
        saveHomeLocality();
        saveVisitLocality();

        ((ArrayList)MainActivity.getReferees()).add(referee);
    }

    private void saveFullName() {
        EditText editTextFirstName = (EditText)findViewById(R.id.createRefereeFirstNameEditText);
        EditText editTextLastName = (EditText)findViewById(R.id.createRefereeLastNameEditText);
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();

        if (!firstName.equals("") || !lastName.equals("")) {
            // First set the FullName and then generate the ID.
            referee.getPerson().setFullName(firstName + " " + lastName);
            referee.getPerson().setID(Person.generateID(MainActivity.getRefereePersons(), firstName + " " + lastName));
        } else {
            showToast("FullName is invalid. Both first and last name required.");
        }
    }

    private void saveVisitLocality() {
        CheckBox checkBoxNorth = (CheckBox)findViewById(R.id.createRefereeCheckboxHomeNorth);
        CheckBox checkBoxCenter = (CheckBox)findViewById(R.id.createRefereeCheckboxHomeCenter);
        CheckBox checkBoxSouth = (CheckBox)findViewById(R.id.createRefereeCheckboxHomeSouth);

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
        int selected = ((RadioGroup)findViewById(R.id.createRefereeRadioGroupHomeLocality)).getCheckedRadioButtonId();

        if (selected == R.id.createRefereeRadioButtonHomeNorth) {
            referee.getLocality().setHome(Area.NORTH);
        } else if (selected == R.id.createRefereeRadioButtonHomeCenter) {
            referee.getLocality().setHome(Area.CENTRAL);
        } else if (selected == R.id.createRefereeRadioButtonHomeSouth) {
            referee.getLocality().setHome(Area.SOUTH);
        }
    }

    private void saveQualification() {
        int selected = ((RadioGroup)findViewById(R.id.createRefereeRadioGrouQualif)).getCheckedRadioButtonId();

        if (selected == R.id.createRefereeRadioButtonNJB) {
            referee.getQualification().setCouncil(Council.NJB);
        } else if (selected == R.id.createRefereeRadioButtonIJB) {
            referee.getQualification().setCouncil(Council.IJB);
        }

        referee.getQualification().setLevel(Integer.parseInt(spinnerLevelSelection));

        referee.setNumOfMatchAllocated(Integer.parseInt(spinnerMatchesSelection));
    }

    public void showToast(String text) {
        if (toast != null) toast.cancel();
        toast = Toast.makeText(ActivityCreateReferee.this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private boolean isProfileValid() {
        boolean areAllFieldsFull = areAllFieldsFull();
        boolean isLocalityValid = isLocalityValid();

        if (!areAllFieldsFull) {
            showToast("All fields must be completed!");
        }

        if (!isLocalityValid) {
            showToast("Home Area must be one of the Visit too!");
        }

        return areAllFieldsFull && isLocalityValid;
    }

    private boolean areAllFieldsFull() {
        EditText editTextFirstName = (EditText)findViewById(R.id.createRefereeFirstNameEditText);
        EditText editTextLastName = (EditText)findViewById(R.id.createRefereeLastNameEditText);
        CheckBox checkBoxNorth = (CheckBox)findViewById(R.id.createRefereeCheckboxHomeNorth);
        CheckBox checkBoxCenter = (CheckBox)findViewById(R.id.createRefereeCheckboxHomeCenter);
        CheckBox checkBoxSouth = (CheckBox)findViewById(R.id.createRefereeCheckboxHomeSouth);

        if (editTextFirstName.getText().toString().trim().equals(""))
            return false;

        if (editTextLastName.getText().toString().trim().equals(""))
            return false;

        if (((RadioGroup)findViewById(R.id.createRefereeRadioGrouQualif)).getCheckedRadioButtonId() == -1)
            return false;

        if (!checkBoxNorth.isChecked() && !checkBoxCenter.isChecked() && !checkBoxSouth.isChecked())
            return false;

        return true;
    }

    private boolean isLocalityValid() {
        CheckBox checkBoxNorth = (CheckBox)findViewById(R.id.createRefereeCheckboxHomeNorth);
        CheckBox checkBoxCenter = (CheckBox)findViewById(R.id.createRefereeCheckboxHomeCenter);
        CheckBox checkBoxSouth = (CheckBox)findViewById(R.id.createRefereeCheckboxHomeSouth);

        int selected = ((RadioGroup)findViewById(R.id.createRefereeRadioGroupHomeLocality)).getCheckedRadioButtonId();

        if (selected == R.id.createRefereeRadioButtonHomeNorth) {
            if (!checkBoxNorth.isChecked())
                return false;
        } else if (selected == R.id.createRefereeRadioButtonHomeCenter) {
            if(!checkBoxCenter.isChecked())
                return false;
        } else if (selected == R.id.createRefereeRadioButtonHomeSouth) {
            if (!checkBoxSouth.isChecked())
                return false;
        }

        return true;
    }

    /**
     * A custom listener for the spinners. Is needed to get the selected values.
     */
    private class MySpinnerOnClickListener implements AdapterView.OnItemSelectedListener {

        private int spinNum = -1;

        public MySpinnerOnClickListener(int i) {
            spinNum = i;
        }

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (spinNum == 0)
                spinnerLevelSelection = parent.getItemAtPosition(pos).toString();
            else if (spinNum == 1)
                spinnerMatchesSelection = parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

}
