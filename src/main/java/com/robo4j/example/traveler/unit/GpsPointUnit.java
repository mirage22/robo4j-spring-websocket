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
import com.robo4j.RoboUnit;
import com.robo4j.configuration.Configuration;
import com.robo4j.example.traveler.RoboTravelerException;
import com.robo4j.example.traveler.gps.GpsPoint;
import com.robo4j.example.traveler.gps.GpxParser;
import com.robo4j.example.traveler.model.GpsConfigMessage;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
public class GpsPointUnit extends RoboUnit<GpsConfigMessage> {
    public static final String NAME = "gpsPointUnit";
    private List<GpsPoint> points;
    private int position = 0;
    private boolean forward = true;

    public GpsPointUnit(RoboContext context, String id) {
        super(GpsConfigMessage.class, context, id);
    }

    @Override
    protected void onInitialization(Configuration configuration) throws ConfigurationException {

        InputStream gpsSource = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("gps/20180314_MunichKussnacht.gpx");
        GpxParser gpxParser = new GpxParser();
        try {
            points = gpxParser.getLocationPoints(gpsSource);
        } catch (Exception e) {
            throw new ConfigurationException("gps source", e);
        }
    }

    @Override
    public void start() {
        super.start();
        if (points == null || points.isEmpty()) {
            throw new RoboTravelerException("geo point are not available");
        }
    }

    @Override
    public void onMessage(GpsConfigMessage message) {
        getContext().getScheduler().scheduleAtFixedRate(this::readGpsPoint, 0,
                message.getSampling(), TimeUnit.MILLISECONDS);
    }

    private void readGpsPoint() {
        System.out.println(getClass().getSimpleName() + ":position:" + position + " and state: " + forward + ":size:" + points.size());

        if (position == 8888) {
            System.out.println("STATUS");
        }
        GpsPoint point = points.get(position);
        getContext().getReference(TravelerUnit.NAME).sendMessage(point);


        if (forward) {
            ++position;
        } else {
            --position;
        }

        if (position % points.size() == 0) {
            System.out.println(getClass().getSimpleName() + ":change1:" + forward);
            forward = !forward;
            position = forward ? ++position : --position;
            System.out.println(getClass().getSimpleName() + ":change2:" + forward);
        }


    }
}
