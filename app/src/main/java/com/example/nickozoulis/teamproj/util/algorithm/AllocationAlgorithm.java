package com.example.nickozoulis.teamproj.util.algorithm;

import com.example.nickozoulis.teamproj.domain.Area;
import com.example.nickozoulis.teamproj.domain.Locality;
import com.example.nickozoulis.teamproj.domain.Referee;
import com.example.nickozoulis.teamproj.util.comparators.SuitabilityBasedRefereeComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nickozoulis on 23/09/2015.
 */
public class AllocationAlgorithm implements Allocation {

    private int level = -1;
    private Area area = null;
    private Collection collection;


    public AllocationAlgorithm() {}

    @Override
    public Collection filter() {
        //TODO: For bigger collections, threaded approach should be followed to avoid UI crash.
        List list = new ArrayList<>(filterBySuitability(collection));

        // Sort suitable referees based on a custom comparator.
        Collections.sort(list, new SuitabilityBasedRefereeComparator(area));

        return list;
    }

    public Set filterBySuitability(Collection collection) {
        Set<Referee> set = new HashSet<>();

        // If filters are not set, set the values to their more general one.
        if (level == -1) setLevel(4);
        if (area == null) setArea(Area.CENTRAL);

        set.addAll(filterByLevel(new HashSet(collection)));
        set.addAll(filterByHomeArea(set));
        set.addAll(filterByAdjacentAndVisitArea(set));
        set.addAll(filterByNonAdjacentAndVisitArea(set));

        return set;
    }

    private Collection filterByHomeArea(Collection collection) {
        Set<Referee> refs = (HashSet<Referee>)collection;
        Set<Referee> filteredCollection = new HashSet<>();

        for (Referee r : refs) {
            if (area == r.getLocality().getHome()) {
                filteredCollection.add(r);
            }
        }

        return filteredCollection;
    }

    private Collection filterByAdjacentAndVisitArea(Collection collection) {
        Set<Referee> refs = (HashSet<Referee>)collection;
        Set<Referee> filteredCollection = new HashSet<>();

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
        Set<Referee> refs = (HashSet<Referee>)collection;
        Set<Referee> filteredCollection = new HashSet<>();

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
        Set<Referee> refs = (HashSet<Referee>)collection;
        Set<Referee> filteredCollection = new HashSet<>();

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

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public void setCollection(Collection collection) {
        this.collection = collection;
    }

}
