/*
 * Copyright (c) 2014, 2018, Marcus Hirt, Miroslav Wengner
 *
 * Robo4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Robo4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Robo4J. If not, see <http://www.gnu.org/licenses/>.
 */

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
