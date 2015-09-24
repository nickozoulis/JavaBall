package com.example.nickozoulis.teamproj.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nickozoulis on 21/09/2015.
 */
public class Locality {

    private Area home;
    private Set<Area> visit;

    public Locality() {
        setVisit(new HashSet<Area>(3));
    }

    public Locality(String area) {
        switch (area) {
            case "North":
                init(Area.NORTH);
                break;
            case "Central":
                init(Area.CENTRAL);
                break;
            case "South":
                init(Area.SOUTH);
                break;
            default:
                throw new IllegalArgumentException("Illegal Argument. Could not instantiate object.");
        }
    }

    private void init(Area area) {
        setHome(area);
        setVisit(new HashSet<Area>(3));
        getVisit().add(area);
    }

    public static List parseVisitLocality(String visitStr) {
        List visitAreas = new ArrayList<Area>();
        char[] charArray = visitStr.toCharArray();

        for (int i=0; i<charArray.length; i++) {
            Character charValue = Character.valueOf(charArray[i]);

            if (charValue == 'Y') {
                visitAreas.add(findVisitArea(i));
            }
        }

        return visitAreas;
    }

    private static Area findVisitArea(int i) {
        switch (i) {
            case 0:
                return Area.NORTH;
            case 1:
                return Area.CENTRAL;
            case 2:
                return Area.SOUTH;
            default:
                throw new IllegalArgumentException("Illegal Argument. Could not instantiate object.");
        }
    }

    public String homeToString() {

        switch (home) {
            case NORTH:
                return "North";
            case CENTRAL:
                return "Central";
            case SOUTH:
                return "South";
            default:
                return super.toString();
        }

    }

    public String visitToString() {
        String s = "";

        if (getVisit().contains(Area.NORTH)) s += "Y";
        else s += "N";

        if (getVisit().contains(Area.CENTRAL)) s += "Y";
        else s += "N";

        if (getVisit().contains(Area.SOUTH)) s += "Y";
        else s += "N";

        return s;
    }

    public String visitToStringInWords() {
        String s = "[";

        if (getVisit().contains(Area.NORTH))
            s += " North ";
        if (getVisit().contains(Area.CENTRAL))
            s += " Central ";
        if (getVisit().contains(Area.SOUTH))
            s += " South ";

        return s + "]";
    }

    public static boolean isAreaAdjacent(Area home, Area area) {
        switch (area) {
            case NORTH:
                if (home == Area.CENTRAL) return true;
                else return false;
            case SOUTH:
                if (home == Area.CENTRAL) return true;
                else return false;
            default:
                return true;
        }
    }


    public Area getHome() {
        return home;
    }

    public void setHome(Area home) {
        this.home = home;
    }

    public Set<Area> getVisit() {
        return visit;
    }

    public void setVisit(Set<Area> visit) {
        this.visit = visit;
    }

}
