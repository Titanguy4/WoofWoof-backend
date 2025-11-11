package com.woofwoof.stayservice.services;

import org.springframework.stereotype.Service;
import com.woofwoof.stayservice.models.*;
import com.woofwoof.stayservice.repositories.StayRepository;

import java.util.List;
import java.util.Optional;
@Service
public class StayService {
    private final StayRepository stayRepository;
    private final GeocodingService reverseGeocodingService;

    public StayService(StayRepository stayRepository, GeocodingService reverseGeocodingService) {
        this.stayRepository = stayRepository;
        this.reverseGeocodingService = reverseGeocodingService;
    }

    public Stay createStay(Stay stay) {
        if (stay.getTitle() == null || stay.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Stay title cannot be null or empty");
        }
        if (stay.getLocalisation() != null && stay.getLocalisation().length == 2) {
            double lon = stay.getLocalisation()[0];
            double lat = stay.getLocalisation()[1];
            GeocodingService.LocationInfo info = reverseGeocodingService.getLocationInfo(lat, lon);
            stay.setDepartment(info.getDepartment());
            stay.setRegion(info.getRegion());
        }
        return stayRepository.save(stay);
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

    public List<Meal> getMealsByStayId(Long stayId) {
        return stayRepository.findMealsByStayId(stayId);
    }

    public List<Accomodation> getAccommodationsByStayId(Long stayId) {
        return stayRepository.findAccommodationsByStayId(stayId);
    }

    public List<Activity> getActivitiesByStayId(Long stayId) {
        return stayRepository.findActivitiesByStayId(stayId);
    }

    public List<LearningSkill> getLearningSkillsByStayId(Long stayId) {
        return stayRepository.findLearningSkillsByStayId(stayId);
    }

    public List<Review> getReviewsByStayId(Long stayId) {
        return stayRepository.findReviewsByStayId(stayId);
    }
    
}