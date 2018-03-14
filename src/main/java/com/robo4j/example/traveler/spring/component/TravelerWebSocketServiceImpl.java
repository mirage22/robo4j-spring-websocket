package com.robo4j.example.traveler.spring.component;

import com.robo4j.example.traveler.gps.GpsPoint;
import com.robo4j.example.traveler.spring.model.TravelerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
@Component
public class TravelerWebSocketServiceImpl implements TravelerWebSocketService {

    private final SimpMessagingTemplate template;

    @Autowired
    public TravelerWebSocketServiceImpl(SimpMessagingTemplate template) {
        this.template = template;
    }


    @Override
    public void sendUpdate(GpsPoint point) {
        TravelerResponse result = new TravelerResponse(point.getLatitude(), point.getLongitude());
        template.convertAndSend("/topic/travelerState", result);
    }
}
