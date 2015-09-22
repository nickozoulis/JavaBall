package com.example.nickozoulis.teamproj.util.threads.io;

import android.os.Environment;
import android.os.Handler;

import com.example.nickozoulis.teamproj.MainActivity;
import com.example.nickozoulis.teamproj.R;
import com.example.nickozoulis.teamproj.domain.Referee;
import com.example.nickozoulis.teamproj.util.threads.UpdateRefereeCollection;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by nickozoulis on 21/09/2015.
 */
public class FileReader implements Runnable {

    private MainActivity mainActivity;
    private Handler handler;
    private Collection referees;
    private File file;

    public FileReader(MainActivity mainActivity, Handler handler) {
        this.mainActivity = mainActivity;
        this.handler = handler;
        this.referees = new ArrayList<Referee>();

        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File (sdCard.getAbsolutePath() + FileWriter.ioDirectory);
        dir.mkdir();

        file = new File(dir, "RefereesIn.txt");
    }

    @Override
    public void run() {
        BufferedReader br;

        try {
            br = new BufferedReader(new java.io.FileReader(file));

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
