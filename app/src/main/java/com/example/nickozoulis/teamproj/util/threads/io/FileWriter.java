package com.example.nickozoulis.teamproj.util.threads.io;

import android.os.Environment;

import com.example.nickozoulis.teamproj.domain.Referee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by nickozoulis on 22/09/2015.
 */
public class FileWriter implements Runnable {

    public static String ioDirectory = "/javaball";
    private File file;
    private ArrayList<Referee> referees;

    public FileWriter(Collection referees) {
        this.referees = (ArrayList<Referee>)referees;

        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File (sdCard.getAbsolutePath() + FileWriter.ioDirectory);
        dir.mkdir();

        file = new File(dir, "RefereesIn.txt");
    }

    @Override
    public void run() {
        try {
            BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(file));

            for (Referee r : referees) {
                bw.write(r.toString());
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
