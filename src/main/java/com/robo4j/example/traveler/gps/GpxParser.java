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

package com.robo4j.example.traveler.gps;

import com.hs.gpxparser.GPXParser;
import com.hs.gpxparser.modal.GPX;
import com.hs.gpxparser.modal.Track;
import com.hs.gpxparser.modal.TrackSegment;
import com.robo4j.example.traveler.RoboTravelerException;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
public class GpxParser {

    private final GPXParser gpxParser = new GPXParser();

    public GpxParser() {
    }

    public List<GpsPoint> getLocationPoints(InputStream inputStream) {
        final GPX gpxResult;
        try {
            gpxResult = gpxParser.parseGPX(inputStream);
        } catch (Exception e) {
            throw new RoboTravelerException("gps", e);
        }

        return gpxResult.getTracks().stream().limit(1)
                .map(Track::getTrackSegments)
                .flatMap(java.util.List::stream)
                .limit(1)
                .map(TrackSegment::getWaypoints)
                .flatMap(java.util.List::stream)
                .map(e -> new GpsPoint(e.getLatitude(), e.getLongitude()))
                .collect(Collectors.toCollection(LinkedList::new));

    }
}
