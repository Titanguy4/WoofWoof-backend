package com.woofwoof.stayservice.services;

import org.springframework.stereotype.Service;

import com.woofwoof.stayservice.repositories.StayserviceRepository;

@Service
public class StayserviceService {
    private final StayserviceRepository stayserviceRepository;
    
    public StayserviceService(StayserviceRepository stayserviceRepository) {
        this.stayserviceRepository = stayserviceRepository;
    }
}
