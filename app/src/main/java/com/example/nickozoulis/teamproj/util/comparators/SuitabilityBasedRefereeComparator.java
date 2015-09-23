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
        int res = firstClassComparison(lRef, rRef);

        if (res == 0) {
            res = secondClassComparison(lRef, rRef);
            if (res == 0) {
                res = thirdClassComparison(lRef, rRef);
            }
        }

        return res;
    }

    private int firstClassComparison(Referee lRef, Referee rRef) {
        int res = compareHomeLocality(lRef, rRef);

        if (res == 0) return compareNumOfAllocations(lRef, rRef);
        else return res;
    }

    private int compareHomeLocality(Referee lRef, Referee rRef) {
        if (area == lRef.getLocality().getHome() && area == rRef.getLocality().getHome()) return 0;
        if (area != lRef.getLocality().getHome() && area != rRef.getLocality().getHome()) return 0;
        if (area == lRef.getLocality().getHome() && area != rRef.getLocality().getHome()) return -1;
        if (area != lRef.getLocality().getHome() && area == rRef.getLocality().getHome()) return 1;

        throw new IllegalStateException("Comparing failed.");
    }

    private int compareNumOfAllocations(Referee lRef, Referee rRef) {
        if (lRef.getNumOfMatchAllocated() > rRef.getNumOfMatchAllocated()) return 1;
        if (lRef.getNumOfMatchAllocated() < rRef.getNumOfMatchAllocated()) return -1;
        if (lRef.getNumOfMatchAllocated() == rRef.getNumOfMatchAllocated()) return 0;

        throw new IllegalStateException("Comparing failed.");
    }

    private int secondClassComparison(Referee lRef, Referee rRef) {
        if (Locality.isAreaAdjacent(lRef.getLocality().getHome(), area) && Locality.isAreaAdjacent(rRef.getLocality().getHome(), area)) {
            return compareVisitLocality(lRef, rRef);
        }

        if (!Locality.isAreaAdjacent(lRef.getLocality().getHome(), area) && Locality.isAreaAdjacent(rRef.getLocality().getHome(), area)) {
            return 1;
        }

        if (Locality.isAreaAdjacent(lRef.getLocality().getHome(), area) && !Locality.isAreaAdjacent(rRef.getLocality().getHome(), area)) {
            return -1;
        }

        throw new IllegalStateException("Comparing failed.");
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

        throw new IllegalStateException("Comparing failed.");
    }

    private int thirdClassComparison(Referee lRef, Referee rRef) {
        if (!Locality.isAreaAdjacent(lRef.getLocality().getHome(), area) && !Locality.isAreaAdjacent(rRef.getLocality().getHome(), area)) {
            int res = compareVisitLocality(lRef, rRef);

            if (res == 0) {
                return compareNumOfAllocations(lRef, rRef);
            } else {
                return res;
            }
        }

        throw new IllegalStateException("Comparing failed.");
    }


}
