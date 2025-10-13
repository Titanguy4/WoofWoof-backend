package com.woofwoof.stayservice.entities;

import org.springframework.web.bind.annotation.RestController;

import com.woofwoof.stayservice.services.StayserviceService;

@RestController
public class StayserviceController {
    private final StayserviceService stayserviceService;
    public StayserviceController(StayserviceService stayserviceService) {
        this.stayserviceService = stayserviceService;
    }
}
