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

package com.robo4j.example.traveler.unit;

import com.robo4j.ConfigurationException;
import com.robo4j.RoboContext;
import com.robo4j.configuration.Configuration;
import com.robo4j.example.traveler.gps.GpsPoint;
import com.robo4j.example.traveler.spring.component.TravelerService;
import com.robo4j.example.traveler.spring.component.TravelerServiceImpl;
import com.robo4j.spring.AbstractSpringUnit;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
public class TravelerUnit extends AbstractSpringUnit<GpsPoint> {

    public static final String NAME = "travelerUnit";

    public static final String PROPERTY_TRAVELER_SERVICE = "travelerService";
    private TravelerService travelerService;
    private String travelerServiceName;

    public TravelerUnit(RoboContext context, String id) {
        super(GpsPoint.class, context, id);
    }

    @Override
    protected void onInitialization(Configuration configuration) throws ConfigurationException {
        travelerServiceName = configuration.getString(PROPERTY_TRAVELER_SERVICE, TravelerServiceImpl.NAME);
        if (travelerServiceName == null) {
            throw new ConfigurationException("travelerServiceName not available");
        }
    }

    @Override
    public void start() {
        super.start();
        travelerService = getComponent(travelerServiceName, TravelerService.class);
    }

    @Override
    public void onMessage(GpsPoint message) {
        System.out.println(getClass().getSimpleName() + ":message:" + message);
        travelerService.setLocation(message);
    }
}
