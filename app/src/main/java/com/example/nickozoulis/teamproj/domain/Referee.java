package com.example.nickozoulis.teamproj.domain;


/**
 * Created by nickozoulis on 21/09/2015.
 */
public class Referee {

    private Person person;
    private Qualification qualification;
    private Locality locality;
    private int numOfMatchAllocated;

    /**
     * Default Constructor
     */
    public Referee () {
        setPerson(new Person());
        setQualification(new Qualification());
        setNumOfMatchAllocated(0);
        setLocality(new Locality());
    }

    public Referee(String line) {
        String[] info = line.split(" ");

        setPerson(new Person(info[0], info[1], info[2]));
        setQualification(new Qualification(info[3]));
        setNumOfMatchAllocated(Integer.parseInt(info[4]));
        initLocality(info[5], info[6]);
    }

    private void initLocality(String homeStr, String visitStr) {
        setLocality(new Locality(homeStr));
        getLocality().getVisit().addAll(Locality.parseVisitLocality(visitStr));
    }

    @Override
    public String toString() {
        String s = "";

        s += getPerson().getID() + " " + getPerson().getFullName() + " " + getQualification() + " "
                + getNumOfMatchAllocated() + " " + getLocality().homeToString() + " " + getLocality().visitToString();

        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Referee) {
            Referee r = (Referee)o;
            if (this.getPerson().equals(r.getPerson()))
                return true;
        }
        return false;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public int getNumOfMatchAllocated() {
        return numOfMatchAllocated;
    }

    public void setNumOfMatchAllocated(int numOfMatchAllocated) {
        this.numOfMatchAllocated = numOfMatchAllocated;
    }

}
