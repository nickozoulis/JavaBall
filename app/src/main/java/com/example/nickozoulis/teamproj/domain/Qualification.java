package com.example.nickozoulis.teamproj.domain;

/**
 * Created by nickozoulis on 21/09/2015.
 */
public class Qualification {

    private Council council;
    private int level;

    public Qualification () {}

    public Qualification(String string) {
        this(string.substring(0, string.length() - 1), Integer.parseInt(string.substring(string.length() - 1)));
    }

    public Qualification(String council, int level) {
        setLevel(level);

        switch (council) {
            case "NJB":
                setCouncil(Council.NJB);
                break;
            case "IJB":
                setCouncil(Council.IJB);
                break;
            default:
                throw new IllegalArgumentException("Illegal Argument. Could not instantiate object.");
        }
    }

    @Override
    public String toString() {

        switch (council) {
            case NJB:
                return "NJB" + level;
            case IJB:
                return "IJB" + level;
            default:
                return super.toString();
        }

    }

    public Council getCouncil() {
        return council;
    }

    public void setCouncil(Council council) {
        this.council = council;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
