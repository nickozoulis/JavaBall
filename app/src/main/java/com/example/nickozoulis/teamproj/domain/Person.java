package com.example.nickozoulis.teamproj.domain;

/**
 * Created by nickozoulis on 21/09/2015.
 */
public class Person {

    private String firstName, lastName, ID;

    public Person(String ID, String firstName, String lastName) {
        setID(ID);
        setFirstName(firstName);
        setLastName(lastName);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {return firstName + " " + lastName;}
}
