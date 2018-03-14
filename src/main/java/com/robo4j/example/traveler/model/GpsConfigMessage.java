package com.robo4j.example.traveler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.TimeUnit;

/**
 * GpsConfigMessage hold information about time pause between two samples
 *
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
@ToString
@AllArgsConstructor
@Getter
public class GpsConfigMessage {
    private int sampling;
}
