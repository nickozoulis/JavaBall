package com.example.nickozoulis.teamproj.domain;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by nickozoulis on 21/09/2015.
 */
public class Match {

    private Collection referees;
    private int week;
    private int level;
    private Area area;

    public Match() {
        setReferees(new ArrayList(2));
    }

    public Collection getReferees() {
        return referees;
    }

    public void setReferees(Collection referees) {
        this.referees = referees;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

}
