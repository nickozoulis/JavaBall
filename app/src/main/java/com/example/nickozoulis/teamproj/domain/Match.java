package com.example.nickozoulis.teamproj.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nickozoulis on 21/09/2015.
 */
public class Match {

    private List referees;

    public Match(List referees) {
        if (referees.size() == 2) {
            setReferees(new ArrayList<Referee>(2));
            getReferees().addAll(referees);
        } else {
            throw new IllegalArgumentException("Match must have two referees.");
        }
    }

    public List getReferees() {
        return referees;
    }

    public void setReferees(List referees) {
        this.referees = referees;
    }
}
