package com.example.nickozoulis.teamproj.util.comparators;

import com.example.nickozoulis.teamproj.domain.Area;
import com.example.nickozoulis.teamproj.domain.Referee;

import java.util.Comparator;

/**
 * Created by nickozoulis on 23/09/2015.
 */
public class SuitabilityBasedRefereeComparator implements Comparator<Referee> {

    private int weekNumber, level;
    private Area area;

    public SuitabilityBasedRefereeComparator(int weekNumber, int level, Area area) {
        this.weekNumber = weekNumber;
        this.level = level;
        this.area = area;
    }

    @Override
    public int compare(Referee lRef, Referee rRef) {




        return 0;
    }
}
