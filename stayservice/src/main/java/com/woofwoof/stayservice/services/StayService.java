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

        // --- GEOLOCATION ---
        if ((stay.getDepartment() == null || stay.getDepartment().isEmpty()) ||
                (stay.getRegion() == null || stay.getRegion().isEmpty())) {

            if (stay.getLocalisation() != null && stay.getLocalisation().length == 2) {
                double lat = stay.getLocalisation()[0];
                double lon = stay.getLocalisation()[1];

                var info = geocodingService.getLocationInfo(lat, lon);

                // On ne remplace que si null
                if (stay.getDepartment() == null || stay.getDepartment().isEmpty()) {
                    stay.setDepartment(info.getDepartment());
                }
                if (stay.getRegion() == null || stay.getRegion().isEmpty()) {
                    stay.setRegion(info.getRegion());
                }
            }
        }

        // --- ACTIVITIES ---
        List<Activity> newActivities = new ArrayList<>();
        for (Activity a : stay.getActivities()) {
            Activity act = Activity.builder()
                    .label(a.getLabel())
                    .stay(stay)
                    .build();
            newActivities.add(act);
        }
        stay.setActivities(newActivities);

        // --- MEALS ---
        List<Meal> newMeals = new ArrayList<>();
        for (Meal m : stay.getMeals()) {
            Meal meal = Meal.builder()
                    .label(m.getLabel())
                    .stay(stay)
                    .build();
            newMeals.add(meal);
        }
        stay.setMeals(newMeals);

        // --- SKILLS ---
        List<LearningSkill> newSkills = new ArrayList<>();
        for (LearningSkill s : stay.getLearningSkills()) {
            LearningSkill skill = LearningSkill.builder()
                    .label(s.getLabel())
                    .stay(stay)
                    .build();
            newSkills.add(skill);
        }
        stay.setLearningSkills(newSkills);

        // --- ACCOMMODATIONS ---
        List<Accomodation> newAcc = new ArrayList<>();
        for (Accomodation a : stay.getAccomodations()) {
            Accomodation acc = Accomodation.builder()
                    .label(a.getLabel())
                    .stay(stay)
                    .build();
            newAcc.add(acc);
        }
        stay.setAccomodations(newAcc);

        // --- SAVE ---
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