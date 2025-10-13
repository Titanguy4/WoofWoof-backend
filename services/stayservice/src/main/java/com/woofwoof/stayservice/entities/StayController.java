package com.woofwoof.stayservice.entities;

import org.springframework.web.bind.annotation.RestController;

import com.woofwoof.stayservice.services.StayService;

@RestController
public class StayController {
    private final StayService stayService;
    public StayController(StayService stayService) {
        this.stayService = stayService;
    }

    
}
