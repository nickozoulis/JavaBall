package com.example.nickozoulis.teamproj.util.threads.io;

import android.os.Handler;

import com.example.nickozoulis.teamproj.MainActivity;
import com.example.nickozoulis.teamproj.R;
import com.example.nickozoulis.teamproj.domain.Referee;
import com.example.nickozoulis.teamproj.util.threads.UpdateRefereeCollection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by nickozoulis on 21/09/2015.
 */
public class FileParser implements Runnable {

    private MainActivity mainActivity;
    private Handler handler;
    private Collection referees;

    public FileParser(MainActivity mainActivity, Handler handler) {
        this.mainActivity = mainActivity;
        this.handler = handler;
        this.referees = new ArrayList<Referee>();
    }

    @Override
    public void run() {
        BufferedReader br;

        try {
            br = new BufferedReader(new InputStreamReader(mainActivity.getResources().openRawResource(R.raw.refereesin)));

            String line = "";
            while ((line = br.readLine()) != null) {
                referees.add(new Referee(line));
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        handler.post(new UpdateRefereeCollection(mainActivity, referees));
    }

}
