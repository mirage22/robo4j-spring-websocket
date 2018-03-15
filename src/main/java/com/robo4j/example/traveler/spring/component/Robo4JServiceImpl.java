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

import com.robo4j.RoboBuilder;
import com.robo4j.RoboContext;
import com.robo4j.RoboReference;
import com.robo4j.example.traveler.model.GpsConfigMessage;
import com.robo4j.example.traveler.unit.GpsPointUnit;
import com.robo4j.example.traveler.unit.TravelerUnit;
import com.robo4j.spring.RoboSpringRegisterUnit;
import com.robo4j.util.PropertyMapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
@Service
public class Robo4JServiceImpl implements Robo4JService {

    private static final int DURATION_SECONDS = 50;
    private static final String ROBO4J_TRAVELER_XML = "robo4j_traveler.xml";
    private final TravelerService travelerService;
    private RoboContext roboSystem;

    @Autowired
    public Robo4JServiceImpl(TravelerService travelerService) {
        this.travelerService = travelerService;
    }

    @Override
    public void start() throws Exception {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        roboSystem = initRoboSystem(classLoader);
        roboSystem.start();
        RoboReference<GpsConfigMessage> gpsPointRef = roboSystem.getReference(GpsPointUnit.NAME);
        GpsConfigMessage message = new GpsConfigMessage(DURATION_SECONDS);
        gpsPointRef.sendMessage(message);
    }


    private RoboContext initRoboSystem(ClassLoader classLoader) throws Exception {

        final RoboBuilder builder = new RoboBuilder()
                .add(classLoader.getResourceAsStream(ROBO4J_TRAVELER_XML));

        final Map<String, Object> springComponents = new PropertyMapBuilder<String, Object>()
                .put(TravelerUnit.PROPERTY_TRAVELER_SERVICE, travelerService)
                .create();

        RoboSpringRegisterUnit registerUnit = new RoboSpringRegisterUnit(null, RoboSpringRegisterUnit.NAME);
        registerUnit.registerComponents(springComponents);

        builder.add(registerUnit);
        return builder.build();

    }
}
