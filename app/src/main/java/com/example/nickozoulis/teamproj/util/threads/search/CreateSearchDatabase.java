package com.example.nickozoulis.teamproj.util.threads.search;

import java.util.ArrayList;

import com.example.nickozoulis.teamproj.activities.MainActivity;
import com.example.nickozoulis.teamproj.domain.Referee;

import android.os.Handler;

public class CreateSearchDatabase implements Runnable {
	
	private MainActivity mainActivity;
	private SearchHelper mDbHelper;
	private Handler handler;
	private ArrayList<Referee> referees;

	public CreateSearchDatabase(MainActivity mainActivity,
			Handler handler, ArrayList<Referee> referees, SearchHelper mDbHelper) {

		this.mainActivity = mainActivity;
		this.handler = handler;
		this.referees = referees;
		this.mDbHelper = mDbHelper;
	}

	@Override
	public void run() {
			mDbHelper = SearchHelper.getSearchHelper(mainActivity);
	
			// Clear all names
			mDbHelper.deleteAllNames();
	
			// Create the list of names which will be displayed on search
			for (Referee r : referees) {
				mDbHelper.createList(r.getPerson().getFullName() + " " + r.getPerson().getID());
			}
			
			handler.post(new UpdateDBThread(mainActivity, mDbHelper));
	}// run

}
