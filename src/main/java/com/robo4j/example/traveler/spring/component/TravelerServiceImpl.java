package com.robo4j.example.traveler.spring.component;

import com.robo4j.example.traveler.gps.GpsPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
@Service
public class TravelerServiceImpl implements TravelerService {

    public static final String NAME = "travelerService";
    private final TravelerWebSocketService travelerWebSocketService;

    @Autowired
    public TravelerServiceImpl(TravelerWebSocketService travelerWebSocketService) {
        this.travelerWebSocketService = travelerWebSocketService;
    }

    @Override
    public void setLocation(GpsPoint point) {
        System.out.println(getClass().getSimpleName() + ":point:" + point);
        travelerWebSocketService.sendUpdate(point);
    }

}
