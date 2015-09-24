package com.example.nickozoulis.teamproj.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.nickozoulis.teamproj.R;
import com.example.nickozoulis.teamproj.adapters.ListAdapterReferee;
import com.example.nickozoulis.teamproj.domain.Person;
import com.example.nickozoulis.teamproj.domain.Referee;
import com.example.nickozoulis.teamproj.util.threads.io.FileWriter;
import com.example.nickozoulis.teamproj.util.threads.search.CreateSearchDatabase;
import com.example.nickozoulis.teamproj.util.threads.io.FileReader;
import com.example.nickozoulis.teamproj.util.comparators.IDBasedRefereeComparator;
import com.example.nickozoulis.teamproj.util.threads.search.SearchHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {

    public static final String REFEREEINFO = "REFEREEINFO";
    public static final int REQUEST_CODE = 1;
    public static final int REQUEST_CODE_MATCH = 2;

    private MainActivity mainActivity;
    private Toast toast;

    private static Collection referees;
    private static Collection matches;

    private ListView listView;
    private ListAdapterReferee listAdapter;

    private SearchView searchView;
    private SearchHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matches = new ArrayList();

        this.mainActivity = this;

        // Main Referee ListView
        // Set ListView reaction when an element is clicked.
        listView = (ListView)findViewById(R.id.refereesListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList arrayList = (ArrayList<Referee>)referees;

                // Start new Activity.
                Intent refereeProfileIntent = new Intent(mainActivity, ActivityViewRefereeProfile.class);
                refereeProfileIntent.putExtra(REFEREEINFO, arrayList.get(position).toString());
                startActivityForResult(refereeProfileIntent, MainActivity.REQUEST_CODE);
            }});

        // Parses the RefereesIn file and initializes collection using thread to avoid UI crashing.
        new Thread(new FileReader(this, new Handler())).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.createReferee) {
            if (MainActivity.getReferees().size() <= 12) {
                // Start new Activity.
                Intent refereeProfileIntent = new Intent(mainActivity, ActivityCreateReferee.class);
                startActivityForResult(refereeProfileIntent, MainActivity.REQUEST_CODE);
            } else {
                showToast("Maximum Referee number is reached! [12]");
            }
        } else if (id == R.id.createMatch) {
            // Start new Activity.
            Intent refereeProfileIntent = new Intent(mainActivity, ActivityCreateMatch.class);
            startActivityForResult(refereeProfileIntent, MainActivity.REQUEST_CODE_MATCH);
        } else if (id == R.id.viewBarChart) {
            // Start new Activity.
            Intent refereeProfileIntent = new Intent(mainActivity, ActivityBarChart.class);
            startActivityForResult(refereeProfileIntent, MainActivity.REQUEST_CODE);
        } else if (id == R.id.exit) {
            saveAndQuit();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveAndQuit() {
        save();
        finish();
    }

    private void save() {
        // Write to file in sd card.
        new Thread(new FileWriter(getReferees(), "RefereesOut.txt")).start();
        new Thread(new FileWriter(getMatches(), "MatchAllocs.txt")).start();
    }

    private void refreshListViewOnCreate(Collection referees) {
        // Sort list first according to referees ID.
        Collections.sort((List)referees, new IDBasedRefereeComparator());

        listAdapter = new ListAdapterReferee(this, R.layout.list_row, (List)referees);
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        initSearchUtil();
    }

    private void refreshListView(Collection referees) {
        // Sort list first according to referees ID.
        Collections.sort((List)referees, new IDBasedRefereeComparator());

        listAdapter = new ListAdapterReferee(this, R.layout.list_row, (List)referees);
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    private void initSearchUtil() {
        // Prepare the SearchView
        searchView = (SearchView) findViewById(R.id.refereeSearchView);

        // Sets the default or resting state of the search field. If true, a
        // single search icon is shown by default and
        // expands to show the text field and other buttons when pressed. Also,
        // if the default state is iconified, then it
        // collapses to that state when the close button is pressed. Changes to
        // this property will take effect immediately.
        // The default value is true.
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

        new Thread(new CreateSearchDatabase(this, new Handler(), (ArrayList)referees, getmDbHelper())).start();
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        displayResults(query + "*");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.isEmpty()) {
            displayResults(newText + "*");
        } else {
            listView.setAdapter(listAdapter);
        }

        return false;
    }

    /*
     * Method used for performing the search and displaying the results. This
     * method is called every time a letter is introduced in the search field.
     *
     * @param query
     * Query used for performing the search
     */
    private void displayResults(String query) {
        if (mDbHelper != null) {
            Cursor cursor = mDbHelper.searchByInputText((query != null ? query
                    : "@@@@"));

            if (cursor != null) {

                String[] from = new String[] { SearchHelper.COLUMN_NAME };

                // Specify the view where we want the results to go
                int[] to = new int[] { R.id.search_result_text_view };

                // Create a simple cursor adapter to keep the search data
                @SuppressWarnings("deprecation")
                SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                        this, R.layout.result_search_item, cursor,
                        from, to);
                listView.setAdapter(cursorAdapter);

                // Click listener for the searched item that was selected
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        // Get the cursor, positioned to the corresponding row in
                        // the result set
                        try {
                            Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                            // Get the state's capital from this row in the database.
                            String selectedName = cursor.getString(cursor
                                    .getColumnIndexOrThrow("name"));

                            // Set the default adapter
                            listView.setAdapter(listAdapter);

                            // Find the position for the original list by the selected
                            // name from search
                            for (int pos = 0; pos < referees.size(); pos++) {
                                if (((ArrayList) referees).get(pos).equals(selectedName)) {
                                    position = pos;
                                    break;
                                }
                            }

                            // Create a handler. This is necessary because the adapter
                            // has just been set on the list again and
                            // the list might not be finished setting the adapter by the
                            // time we perform setSelection.
                            Handler handler = new Handler();
                            final int finalPosition = position;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setSelection(finalPosition);
                                }
                            });

                            searchView.setQuery("", true);

                            // Code when search result is clicked
                            // Find referee clicked.
                            Referee refereeClicked = findRefereeByName(selectedName);

                            // Start new Activity.
                            Intent refereeProfileIntent = new Intent(mainActivity, ActivityViewRefereeProfile.class);
                            refereeProfileIntent.putExtra(MainActivity.REFEREEINFO, refereeClicked.toString());
                            startActivityForResult(refereeProfileIntent, MainActivity.REQUEST_CODE);
                        } catch (ClassCastException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }// if (cursor != null)
        }// if (mDbHelper != null)
    }// displayResults

    private Referee findRefereeByName(String selectedName) {
        String refereeID = selectedName.substring(selectedName.length()-3);
        List<Referee> refList = (ArrayList)referees;
        for (Referee r : refList) {
            if (r.getPerson().getID().equals(refereeID))
                return r;
        }

        throw new IllegalArgumentException("Search was not found.");
    }

    public void showToast(String text) {
        if (toast != null) toast.cancel();
        toast = Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public SearchHelper getmDbHelper() {
        return mDbHelper;
    }

    public void setmDbHelper(SearchHelper mDbHelper) {
        this.mDbHelper = mDbHelper;
    }
    public static Collection getReferees() {
        return referees;
    }

    public static Collection getRefereePersons() {
        ArrayList<Referee> refs = (ArrayList<Referee>)referees;
        Collection<Person> persons = new ArrayList<Person>(12);

        for (Referee r : refs) {
            persons.add(r.getPerson());
        }

        return persons;
    }

    public void setReferees(Collection referees) {
        this.referees = referees;
        refreshListViewOnCreate(referees);
    }

    public static Collection getMatches() {
        return matches;
    }

    public static void setMatches(Collection matches) {
        MainActivity.matches = matches;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.REQUEST_CODE && resultCode == RESULT_OK) {
                refreshListViewOnCreate(getReferees());
        } else if (requestCode == MainActivity.REQUEST_CODE && resultCode < RESULT_OK) {
            showToast("Referee was succesfully deleted!");
            // Refresh ListView
            refreshListView(getReferees());
        } else if (requestCode == MainActivity.REQUEST_CODE_MATCH && resultCode == RESULT_OK) {
            showToast("Match was succesfully saved!");
            refreshListView(getReferees());
        }
    }

}
