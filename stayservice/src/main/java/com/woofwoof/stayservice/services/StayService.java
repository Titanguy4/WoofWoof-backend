package com.woofwoof.stayservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.woofwoof.stayservice.entities.Review;
import com.woofwoof.stayservice.entities.Stay;
import com.woofwoof.stayservice.entities.subclass.Accomodation;
import com.woofwoof.stayservice.entities.subclass.Activity;
import com.woofwoof.stayservice.entities.subclass.LearningSkill;
import com.woofwoof.stayservice.entities.subclass.Meal;
import com.woofwoof.stayservice.repositories.StayRepository;
import com.woofwoof.stayservice.repositories.subclass.AccomodationRepository;
import com.woofwoof.stayservice.repositories.subclass.ActivityRepository;
import com.woofwoof.stayservice.repositories.subclass.LearningSkillRepository;
import com.woofwoof.stayservice.repositories.subclass.MealRepository;

@Service
public class StayService {
    private final StayRepository stayRepository;
    private final GeocodingService geocodingService;

    private final ActivityRepository activityRepo;
    private final MealRepository mealRepo;
    private final LearningSkillRepository skillRepo;
    private final AccomodationRepository accomodationRepo;

    public StayService(
            StayRepository stayRepository,
            GeocodingService geocodingService,
            ActivityRepository activityRepo,
            MealRepository mealRepo,
            LearningSkillRepository skillRepo,
            AccomodationRepository accomodationRepo) {
        this.stayRepository = stayRepository;
        this.geocodingService = geocodingService;
        this.activityRepo = activityRepo;
        this.mealRepo = mealRepo;
        this.skillRepo = skillRepo;
        this.accomodationRepo = accomodationRepo;
    }

    public Stay createStay(Stay stay) {

        if (stay.getTitle() == null || stay.getTitle().isEmpty())
            throw new IllegalArgumentException("Stay title cannot be null or empty");

        if (stay.getLocalisation() != null && stay.getLocalisation().length == 2) {

            double lat = stay.getLocalisation()[0]; // JSON = [lat, lon]
            double lon = stay.getLocalisation()[1];

            var info = geocodingService.getLocationInfo(lat, lon);
            stay.setDepartment(info.getDepartment());
            stay.setRegion(info.getRegion());
        }

        List<Activity> finalActivities = new ArrayList<>();
        for (Activity a : stay.getActivities()) {
            Activity db = activityRepo.findById(a.getId())
                    .orElseThrow(() -> new RuntimeException("Activity not found: " + a.getId()));
            db.setStay(stay);
            finalActivities.add(db);
        }
        stay.setActivities(finalActivities);

        List<Meal> finalMeals = new ArrayList<>();
        for (Meal m : stay.getMeals()) {
            Meal db = mealRepo.findById(m.getId())
                    .orElseThrow(() -> new RuntimeException("Meal not found: " + m.getId()));
            db.setStay(stay);
            finalMeals.add(db);
        }
        stay.setMeals(finalMeals);

        List<LearningSkill> finalSkills = new ArrayList<>();
        for (LearningSkill s : stay.getLearningSkills()) {
            LearningSkill db = skillRepo.findById(s.getId())
                    .orElseThrow(() -> new RuntimeException("Skill not found: " + s.getId()));
            db.setStay(stay);
            finalSkills.add(db);
        }
        stay.setLearningSkills(finalSkills);

        List<Accomodation> finalAcc = new ArrayList<>();
        for (Accomodation ac : stay.getAccomodations()) {
            Accomodation db = accomodationRepo.findById(ac.getId())
                    .orElseThrow(() -> new RuntimeException("Accomodation not found: " + ac.getId()));
            db.setStay(stay);
            finalAcc.add(db);
        }
        stay.setAccomodations(finalAcc);

        return stayRepository.save(stay);
    }

    public Stay getStayById(Long id) {
        return stayRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stay not found"));
    }

    public void deleteStay(Long id) {
        stayRepository.deleteById(id);
    }

    public Stay updateStay(Stay stay) {
        if (!stayRepository.existsById(stay.getId())) {
            throw new RuntimeException("Stay with id " + stay.getId() + " does not exist.");
        } else {
            return stayRepository.save(stay);
        }
    }

    public List<Meal> getMealsById(Long id) {
        Stay stay = stayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stay not found"));
        return stay.getMeals();
    }

    public List<Accomodation> getAccommodationsById(Long id) {
        Stay stay = stayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stay not found"));
        return stay.getAccomodations();
    }

    public List<Activity> getActivitiesById(Long id) {
        Stay stay = stayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stay not found"));
        return stay.getActivities();
    }

    public List<LearningSkill> getLearningSkillsById(Long id) {
        Stay stay = stayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stay not found"));
        return stay.getLearningSkills();
    }

    public List<Review> getReviewsById(Long id) {
        Stay stay = stayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stay not found"));
        return stay.getReviews();
    }

    public List<Long> getStayIdsByWooferId(UUID wooferId) {
    return stayRepository.findByWooferId(wooferId)
            .stream()
            .map(Stay::getId)
            .toList();
}



}