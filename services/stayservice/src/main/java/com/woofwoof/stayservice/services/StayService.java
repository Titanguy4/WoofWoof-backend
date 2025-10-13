package com.woofwoof.stayservice.services;

import org.springframework.stereotype.Service;

import com.woofwoof.stayservice.repositories.StayRepository;

@Service
public class StayService {
    private final StayRepository stayRepository;
    
    public StayService(StayRepository stayRepository) {
        this.stayRepository = stayRepository;
    }
}
