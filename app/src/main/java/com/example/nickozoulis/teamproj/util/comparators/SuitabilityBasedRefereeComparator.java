package com.example.nickozoulis.teamproj.util.comparators;

import com.example.nickozoulis.teamproj.domain.Area;
import com.example.nickozoulis.teamproj.domain.Locality;
import com.example.nickozoulis.teamproj.domain.Referee;

import java.util.Comparator;

/**
 * Created by nickozoulis on 23/09/2015.
 */
public class SuitabilityBasedRefereeComparator implements Comparator<Referee> {

    private Area area;

    public SuitabilityBasedRefereeComparator(Area area) {
        this.area = area;
    }

    @Override
    public int compare(Referee lRef, Referee rRef) {
        switch (compareHomeLocality(lRef, rRef)) {
            case -1:
                return -1;
            case 0:
                return compareNumOfAllocations(lRef, rRef);
            case 1:
                return 1;
            default:
                throw new IllegalStateException("Comparing failed.");
        }
    }

    private int compareHomeLocality(Referee lRef, Referee rRef) {
        if (area == lRef.getLocality().getHome() && area == rRef.getLocality().getHome()) return 0;
        if (area != lRef.getLocality().getHome() && area != rRef.getLocality().getHome()) return 0;
        if (area == lRef.getLocality().getHome() && area != rRef.getLocality().getHome()) return 1;
        if (area != lRef.getLocality().getHome() && area == rRef.getLocality().getHome()) return -1;

        throw new IllegalStateException("Comparing failed.");
    }

    private int compareNumOfAllocations(Referee lRef, Referee rRef) {
        if (lRef.getNumOfMatchAllocated() > rRef.getNumOfMatchAllocated()) return 1;
        if (lRef.getNumOfMatchAllocated() < rRef.getNumOfMatchAllocated()) return -1;
        if (lRef.getNumOfMatchAllocated() == rRef.getNumOfMatchAllocated()) return 0;//return compareVisitLocality(lRef, rRef);

        throw new IllegalStateException("Comparing failed.");
    }

    private int compareVisitLocality(Referee lRef, Referee rRef) {
        return 0;
    }


}
