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

    public Stay updateStay(Long id, Stay updatedStay) {
        return stayRepository.findById(id)
        .map(stay -> {
            stay.setTitle(updatedStay.getTitle());
            stay.setDescription(updatedStay.getDescription());
            stay.setLocalisation(updatedStay.getLocalisation());
            stay.setStartDate(updatedStay.getStartDate());
            stay.setEndDate(updatedStay.getEndDate());
            stay.setStatus(updatedStay.getStatus());
            stay.setActivities(updatedStay.getActivities());
            stay.setLearningSkills(updatedStay.getLearningSkills());
            stay.setMeals(updatedStay.getMeals());
            stay.setAccomodations(updatedStay.getAccomodations());
            stay.setPhotos(updatedStay.getPhotos());
            return stayRepository.save(stay);
        })
        .orElseThrow(() -> new RuntimeException("Stay not found"));
    }
}
