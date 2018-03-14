package com.robo4j.example.traveler;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
public class RoboTravelerException extends RuntimeException{

    public RoboTravelerException(String message) {
        super(message);
    }

    public RoboTravelerException(String message, Throwable cause) {
        super(message, cause);
    }
}
