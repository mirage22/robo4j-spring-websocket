package com.robo4j.example.traveler.spring.component;

import com.robo4j.example.traveler.gps.GpsPoint;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
public interface TravelerWebSocketService {

    void sendUpdate(GpsPoint point);
}
