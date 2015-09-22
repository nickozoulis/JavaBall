package com.example.nickozoulis.teamproj;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nickozoulis.teamproj.domain.Referee;

import java.util.ArrayList;

public class ActivityRefereeProfile extends AppCompatActivity {

    private Toast toast;
    private Referee referee;
    private int refereeIndex;
    private Activity activityRefereeProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referee_profile);

        activityRefereeProfile = this;

        Bundle b = getIntent().getExtras();

        // Load selected Referee's reference from Main Collection.
        refereeIndex = ((ArrayList)MainActivity.getReferees()).indexOf(new Referee((String)b.get(MainActivity.REFEREEINFO)));
        referee = (Referee)((ArrayList) MainActivity.getReferees()).get(refereeIndex);

        setupUI(referee);
    }

    private void setupUI(Referee referee) {
        TextView textViewProfileID = (TextView)findViewById(R.id.activity_referee_profile_ID_textView);
        TextView textViewFullName = (TextView)findViewById(R.id.activity_referee_profile_fullname);
        TextView textViewQualification = (TextView)findViewById(R.id.activity_referee_profile_qualification_textview);
        TextView textViewHomeLocality = (TextView)findViewById(R.id.activity_referee_profile_homeLocality_textview);
        TextView textViewVisitLocality = (TextView)findViewById(R.id.activity_referee_profile_visitLocality_textview);

        textViewProfileID.setText(referee.getPerson().getID());
        textViewFullName.setText(referee.getPerson().getFullName());
        textViewQualification.setText(referee.getQualification().toString());
        textViewHomeLocality.setText(referee.getLocality().homeToString());
        textViewVisitLocality.setText(referee.getLocality().visitToString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_referee_profile, menu);
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
        } else if (id == R.id.buttonEditRefereeDetails) {
            // Start new Activity.
            Intent editRefereeProfileIntent = new Intent(activityRefereeProfile, ActivityEditRefereeProfile.class);
            editRefereeProfileIntent.putExtra(MainActivity.REFEREEINFO, referee.toString());
            startActivityForResult(editRefereeProfileIntent, MainActivity.REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    public void showToast(String text) {
        if (toast != null) toast.cancel();
        toast = Toast.makeText(ActivityRefereeProfile.this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            // Load selected Referee's reference and refresh UI with any changes.
            Referee r = (Referee)((ArrayList) MainActivity.getReferees()).get(refereeIndex);
            setupUI(r);
            referee = r;
        } else if (requestCode == MainActivity.REQUEST_CODE && resultCode < RESULT_OK) {
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK - 1, resultIntent);
            finish();
            finish();
        }
    }
}
