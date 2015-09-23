package com.example.nickozoulis.teamproj.util.threads.handlers;

import com.example.nickozoulis.teamproj.activities.MainActivity;

import java.util.Collection;

/**
 * Created by nickozoulis on 21/09/2015.
 */
public class UpdateRefereeCollection implements Runnable {

    private MainActivity mainActivity;
    private Collection referees;

    public UpdateRefereeCollection(MainActivity mainActivity, Collection referees) {
        this.mainActivity = mainActivity;
        this.referees = referees;
    }

    @Override
    public void run() {
        mainActivity.setReferees(referees);
    }
}
