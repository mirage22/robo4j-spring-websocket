package com.robo4j.example.traveler.spring.component;

import com.robo4j.example.traveler.gps.GpsPoint;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
public interface TravelerService {

    void setLocation(GpsPoint point);

}
