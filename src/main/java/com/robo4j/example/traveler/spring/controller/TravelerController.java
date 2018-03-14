package com.robo4j.example.traveler.spring.controller;

import com.robo4j.example.traveler.RoboTravelerException;
import com.robo4j.example.traveler.spring.component.Robo4JService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
@Controller
public class TravelerController {

    @Value("${application.title}")
    private String title;

    @Value("${application.description}")
    private String description;

    private final Robo4JService robo4JService;

    @Autowired
    public TravelerController(Robo4JService robo4JService) {
        this.robo4JService = robo4JService;
    }

    @GetMapping("/")
    public String socket(Map<String, Object> model) {
        try {
            robo4JService.start();
        } catch (Exception e) {
            throw new RoboTravelerException("not possible", e);
        }
        model.put("title", title);
        model.put("description", description);
        return "traveler";
    }
}
