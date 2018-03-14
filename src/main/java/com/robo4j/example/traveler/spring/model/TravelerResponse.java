package com.robo4j.example.traveler.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelerResponse {
    private Double latitude;
    private Double longitude;
}
