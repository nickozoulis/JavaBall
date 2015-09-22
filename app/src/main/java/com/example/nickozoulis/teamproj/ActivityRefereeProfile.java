package com.example.nickozoulis.teamproj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.nickozoulis.teamproj.domain.Locality;
import com.example.nickozoulis.teamproj.domain.Referee;

public class ActivityRefereeProfile extends AppCompatActivity {

    private Referee referee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referee_profile);

        Bundle b = getIntent().getExtras();

        referee = new Referee((String)b.get(MainActivity.REFEREEINFO));

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
        }

        return super.onOptionsItemSelected(item);
    }
}
