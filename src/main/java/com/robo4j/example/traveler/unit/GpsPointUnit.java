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

    private int position;
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
                message.getSampling(), TimeUnit.SECONDS);
    }

    private void readGpsPoint() {
        GpsPoint point = points.get(position);
        System.out.println(getClass().getSimpleName() + ":point:" + point + ":position:" + position);
        getContext().getReference(TravelerUnit.NAME).sendMessage(point);

        if (position == points.size() && forward) {
            forward = false;
        } else if (position == 0 && !forward) {
            forward = true;
        }

        if(forward){
            ++position;
        } else {
            --position;
        }

    }
}
