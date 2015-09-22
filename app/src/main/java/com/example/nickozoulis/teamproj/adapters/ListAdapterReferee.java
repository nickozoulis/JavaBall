package com.example.nickozoulis.teamproj.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nickozoulis.teamproj.R;
import com.example.nickozoulis.teamproj.domain.Referee;

import java.util.List;

/**
 * Created by nickozoulis on 21/09/2015.
 */
public class ListAdapterReferee extends ArrayAdapter<Referee> {

    public ListAdapterReferee(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapterReferee(Context context, int resource, List<Referee> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_row, null);
        }

        Object p = getItem(position);
        Referee r = (Referee)p;

        if (r != null) {

            TextView refereeID = (TextView) v.findViewById(R.id.refereeIDTextView);
            TextView refereeFullName = (TextView) v.findViewById(R.id.refereeFullNameTextView);
            TextView refereeQualification = (TextView) v.findViewById(R.id.refereeQualificationTextView);
            TextView refereeNumOfAllocatedMatches = (TextView) v.findViewById(R.id.refereeNumOfMatchesAllocatedTextView);
            TextView refereeHomeLocality = (TextView) v.findViewById(R.id.refereeHomeLocalityTextView);
            TextView refereeVisitLocality = (TextView) v.findViewById(R.id.refereeVisitLocalityTextView);


            if (refereeID != null)
                refereeID.setText(r.getPerson().getID());
            if (refereeFullName != null)
                refereeFullName.setText(r.getPerson().getFullName());
            if (refereeQualification != null)
                refereeQualification.setText(r.getQualification().toString());
            if (refereeNumOfAllocatedMatches != null)
                refereeNumOfAllocatedMatches.setText(Integer.toString(r.getNumOfMatchAllocated()));
            if (refereeHomeLocality != null)
                refereeHomeLocality.setText(r.getLocality().homeToString());
            if (refereeVisitLocality != null)
                refereeVisitLocality.setText(r.getLocality().visitToString());

        }

        return v;
    }

}
