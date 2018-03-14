package com.robo4j.example.traveler.gps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class GpsPoint implements GpsElement {

    private Double latitude;
    private Double longitude;

}
