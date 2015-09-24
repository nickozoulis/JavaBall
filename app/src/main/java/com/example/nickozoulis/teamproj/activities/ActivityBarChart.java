package com.example.nickozoulis.teamproj.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nickozoulis.teamproj.R;
import com.example.nickozoulis.teamproj.domain.Referee;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collection;

public class ActivityBarChart extends AppCompatActivity {

    private ArrayList<Referee> referees;
    private BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        referees = (ArrayList<Referee>)MainActivity.getReferees();

        setupUI();
    }

    private void setupUI() {
        chart = (BarChart) findViewById(R.id.chart);

        ArrayList<String> xValues = formatXvalues();
        BarDataSet yValues = formatYvalues();

        BarData data = new BarData(xValues, yValues);
        chart.setData(data);
        // Refresh
        chart.invalidate();
    }

    private ArrayList<String> formatXvalues() {
        ArrayList<String> values = new ArrayList<>();

        for (Referee r : referees) {
            values.add(r.getPerson().getID());
        }

        return values;
    }

    private BarDataSet formatYvalues() {
        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i=0; i<referees.size(); i++) {
            values.add(new BarEntry(referees.get(i).getNumOfMatchAllocated(), i));
        }

        BarDataSet barDataSet = new BarDataSet(values, "Referees");
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        return barDataSet;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_bar_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
