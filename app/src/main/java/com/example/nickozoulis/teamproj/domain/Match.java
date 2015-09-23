package com.example.nickozoulis.teamproj.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by nickozoulis on 21/09/2015.
 */
public class Match {

    private Collection referees;
    private int week;
    private int level;
    private Area area;

    public Match(int week, int level, Area area) {
        setWeek(week);
        setLevel(level);
        setArea(area);
        setReferees(new HashSet(2));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Match) {
            Match m = (Match)o;
            if (this.getWeek() == m.getWeek()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        List<Referee> l = (List)getReferees();

        String s = "";

        s += getWeek() + " " + lvlToString(getLevel()) + " " + getArea() + " "
                + l.get(0).getPerson().getFullName() + " " + l.get(1).getPerson().getFullName();

        return  s;
    }

    private String lvlToString(int lvl) {
        String s = "";

        if (lvl == 1) {
            s = "Junior";
        } else if (lvl >= 2) {
            s = "Senior";
        }

        return s;
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
