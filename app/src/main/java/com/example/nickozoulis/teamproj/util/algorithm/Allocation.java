package com.example.nickozoulis.teamproj.util.algorithm;

import com.example.nickozoulis.teamproj.domain.Area;

import java.util.Collection;

/**
 * Created by nickozoulis on 23/09/2015.
 */
public interface Allocation {

    public Collection filter();
    public void setCollection(Collection collection);
    public void setLevel(int level);
    public void setArea(Area area);

}
