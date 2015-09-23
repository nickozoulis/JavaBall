package com.example.nickozoulis.teamproj.util.algorithm;

import com.example.nickozoulis.teamproj.domain.Area;
import com.example.nickozoulis.teamproj.domain.Locality;
import com.example.nickozoulis.teamproj.domain.Referee;
import com.example.nickozoulis.teamproj.util.comparators.SuitabilityBasedRefereeComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by nickozoulis on 23/09/2015.
 */
public class AllocationAlgorithm implements Allocation {

    private int level;
    private Area area;
    Collection collection;

    public AllocationAlgorithm(int level, Area area, Collection collection) {
        this.level = level;
        this.area = area;
        this.collection = collection;
    }

    @Override
    public Collection filter() {
        if ((collection = filterBySuitability(collection)) != null && collection.size() >= 2) {
            // Sort suitable referees based on a custom comparator.
            Collections.sort((List) collection, new SuitabilityBasedRefereeComparator(area));
            return collection;
        }
        else
            return null;
    }

    public Collection filterBySuitability(Collection collection) {
        if ((collection = filterByLevel(collection)).size() < 2) return null;

        if ((collection = filterByHomeArea(collection)).size() >= 2)
            return collection;
        else
            if ((collection = filterByAdjacentAndVisitArea(collection)).size() >= 2)
                return collection;
            else
                if ((collection = filterByNonAdjacentAndVisitArea(collection)).size() >= 2)
                    return collection;
                else
                    return null;
    }

    private Collection filterByHomeArea(Collection collection) {
        ArrayList<Referee> refs = (ArrayList<Referee>)collection;
        ArrayList<Referee> filteredCollection = new ArrayList<Referee>();

        for (Referee r : refs) {
            if (area == r.getLocality().getHome()) {
                filteredCollection.add(r);
            }
        }

        return filteredCollection;
    }

    private Collection filterByAdjacentAndVisitArea(Collection collection) {
        ArrayList<Referee> refs = (ArrayList<Referee>)collection;
        ArrayList<Referee> filteredCollection = new ArrayList<Referee>();

        for (Referee r : refs) {
            if (Locality.isAreaAdjacent(r.getLocality().getHome(), area)) {
                if (r.getLocality().getVisit().contains(area)) {
                    filteredCollection.add(r);
                }
            }
        }

        return filteredCollection;
    }

    private Collection filterByNonAdjacentAndVisitArea(Collection collection) {
        ArrayList<Referee> refs = (ArrayList<Referee>)collection;
        ArrayList<Referee> filteredCollection = new ArrayList<Referee>();

        for (Referee r : refs) {
            if (!Locality.isAreaAdjacent(r.getLocality().getHome(), area)) {
                if (r.getLocality().getVisit().contains(area)) {
                    filteredCollection.add(r);
                }
            }
        }

        return filteredCollection;
    }

    private Collection filterByLevel(Collection collection) {
        ArrayList<Referee> refs = (ArrayList<Referee>)collection;
        ArrayList<Referee> filteredCollection = new ArrayList<Referee>();

        for (Referee r : refs) {
            if (level == 1) {
                if (r.getQualification().getLevel() == 1) {
                    filteredCollection.add(r);
                }
            } else if (level >= 2) {
                if (r.getQualification().getLevel() >= 2) {
                    filteredCollection.add(r);
                }
            }
        }

        return filteredCollection;
    }


}
