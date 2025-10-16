package com.woofwoof.stayservice.services;

import org.springframework.stereotype.Service;
import com.woofwoof.stayservice.models.*;
import com.woofwoof.stayservice.repositories.StayRepository;

import java.util.List;
import java.util.Optional;
@Service
public class StayService {
    private final StayRepository stayRepository;
    
    public StayService(StayRepository stayRepository) {
        this.stayRepository = stayRepository;
    }

    public Stay createStay(Stay stay) {
        return stayRepository.save(stay);
    }

    public List<Stay> getAllStays() {
        return stayRepository.findAll();
    }

    public Optional<Stay> getStayById(Long id) {
        return stayRepository.findById(id);
    }

    public void deleteStay(Long id) {
        stayRepository.deleteById(id);
    }

    public Stay updateStay(Stay stay) {
        if(!stayRepository.existsById(stay.getId_stay())) {
            throw new RuntimeException("Stay with id " + stay.getId_stay() + " does not exist.");
        } else {
            return stayRepository.save(stay);
        }
    }
}