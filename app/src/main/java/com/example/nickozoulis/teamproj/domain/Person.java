package com.example.nickozoulis.teamproj.domain;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by nickozoulis on 21/09/2015.
 */
public class Person {

    private String firstName, lastName, ID;

    public Person() {}

    public Person(String ID, String firstName, String lastName) {
        setID(ID);
        setFirstName(firstName);
        setLastName(lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Person) {
            Person p = (Person)o;
            if (this.getID().equals(p.getID()))
                return true;
        }
        return false;
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

    public void setFullName(String fullName) {
        String[] array = fullName.split(" ");

        setFirstName(array[0]);
        setLastName(array[1]);
    }

    /**
     * If initials exist, it finds the first available number for the ID.
     * @param referees
     * @param fullName
     * @return
     */
    public static String generateID(Collection<Person> referees, String fullName) {
        ArrayList<Person> persons = (ArrayList<Person>)referees;
        String[] array = fullName.split(" ");

        String s = "";

        String initials = array[0].substring(0, 1) + "" + array[1].substring(0, 1);
        int availableNum = 1;
        boolean initialsExist = false;

        for (Person p : persons) {
            String tempInitials = p.getID().substring(0, 2);

            if (initials.equals(tempInitials)) {
                initialsExist = true;
            }
        }

        if (initialsExist) {
            while (!isNumAvailable(initials, availableNum, persons)) {
                availableNum++;
            }

            s = initials + availableNum;
        } else {
            s = initials + availableNum;
        }

        return s;
    }

    private static boolean isNumAvailable(String s, int i, ArrayList<Person> persons) {
        s += i;

        for (Person p : persons) {
            if (s.equals(p.getID()))
                return false;
        }

        return true;
    }

}
