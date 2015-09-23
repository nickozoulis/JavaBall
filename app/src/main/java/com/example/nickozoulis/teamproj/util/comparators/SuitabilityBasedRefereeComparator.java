package com.example.nickozoulis.teamproj.util.comparators;

import android.util.Log;

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
        int res = firstClassOrdering(lRef, rRef);

        if (res == 0) {
            res = secondClassOrdering(lRef, rRef);
            if (res == 0) {
                res = thirdClassOrdering(lRef, rRef);
            }
        }

        return res;
    }

    private int firstClassOrdering(Referee lRef, Referee rRef) {
        int res = compareHomeLocality(lRef, rRef);

        if (res == 0) return compareNumOfAllocations(lRef, rRef);
        else if (res == -2) return 0;
        else return res;
    }

    private int compareHomeLocality(Referee lRef, Referee rRef) {
        if (area == lRef.getLocality().getHome() && area == rRef.getLocality().getHome()) return 0;
        if (area == lRef.getLocality().getHome() && area != rRef.getLocality().getHome()) return -1;
        if (area != lRef.getLocality().getHome() && area == rRef.getLocality().getHome()) return 1;

        if (area != lRef.getLocality().getHome() && area != rRef.getLocality().getHome()) return -2;

        return 0;
    }

    private int compareNumOfAllocations(Referee lRef, Referee rRef) {
        if (lRef.getNumOfMatchAllocated() > rRef.getNumOfMatchAllocated()) return 1;
        if (lRef.getNumOfMatchAllocated() < rRef.getNumOfMatchAllocated()) return -1;
        if (lRef.getNumOfMatchAllocated() == rRef.getNumOfMatchAllocated()) return 0;

        return 0;
    }

    private int secondClassOrdering(Referee lRef, Referee rRef) {
        if (Locality.isAreaAdjacent(lRef.getLocality().getHome(), area) && Locality.isAreaAdjacent(rRef.getLocality().getHome(), area)) {
            return compareVisitLocality(lRef, rRef);
        }

        if (!Locality.isAreaAdjacent(lRef.getLocality().getHome(), area) && Locality.isAreaAdjacent(rRef.getLocality().getHome(), area)) {
            return 1;
        }

        if (Locality.isAreaAdjacent(lRef.getLocality().getHome(), area) && !Locality.isAreaAdjacent(rRef.getLocality().getHome(), area)) {
            return -1;
        }

        return 0;
    }

    private int compareVisitLocality(Referee lRef, Referee rRef) {
        if (lRef.getLocality().getVisit().contains(area) && rRef.getLocality().getVisit().contains(area)) {
            return compareNumOfAllocations(lRef, rRef);
        }

        if (!lRef.getLocality().getVisit().contains(area) && rRef.getLocality().getVisit().contains(area)) {
            return 1;
        }

        if (lRef.getLocality().getVisit().contains(area) && !rRef.getLocality().getVisit().contains(area)) {
            return -1;
        }

        return 0;
    }

    private int thirdClassOrdering(Referee lRef, Referee rRef) {
        if (!Locality.isAreaAdjacent(lRef.getLocality().getHome(), area) && !Locality.isAreaAdjacent(rRef.getLocality().getHome(), area)) {
            int res = compareVisitLocality(lRef, rRef);

            if (res == 0) {
                return compareNumOfAllocations(lRef, rRef);
            }
        }

        return 0;
    }

}
