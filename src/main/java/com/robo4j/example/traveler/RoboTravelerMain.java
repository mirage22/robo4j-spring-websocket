package com.robo4j.example.traveler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
@EnableAsync
@SpringBootApplication
@EnableWebSocketMessageBroker
public class RoboTravelerMain {
    public static void main(String[] args) {
        SpringApplication.run(RoboTravelerMain.class, args);
    }
}
