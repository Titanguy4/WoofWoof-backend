package com.woofwoof.stayservice.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
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
import com.woofwoof.stayservice.utils.GeoUtils;

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

    // --- Auto-fill department & region if coordinates present ---
    if (stay.getLocalisation() != null && stay.getLocalisation().length == 2) {

        double lat = stay.getLocalisation()[0];
        double lon = stay.getLocalisation()[1];

        var info = geocodingService.getLocationInfo(lat, lon);

        if (stay.getDepartment() == null || stay.getDepartment().isEmpty()) {
            stay.setDepartment(info.getDepartment());
        }
        if (stay.getRegion() == null || stay.getRegion().isEmpty()) {
            stay.setRegion(info.getRegion());
        }
    }

    // -------- ACTIVITIES --------
    List<Activity> finalActivities = new ArrayList<>();
    for (Activity a : stay.getActivities()) {
        Activity db;

        if (a.getId() != null) {
            db = activityRepo.findById(a.getId())
                    .orElseThrow(() -> new RuntimeException("Activity not found: " + a.getId()));
        } else {
            // create new
            db = Activity.builder()
                    .label(a.getLabel())
                    .stay(stay)
                    .build();
        }

        db.setStay(stay);
        finalActivities.add(db);
    }
    stay.setActivities(finalActivities);

    // -------- MEALS --------
    List<Meal> finalMeals = new ArrayList<>();
    for (Meal m : stay.getMeals()) {
        Meal db;

        if (m.getId() != null) {
            db = mealRepo.findById(m.getId())
                    .orElseThrow(() -> new RuntimeException("Meal not found: " + m.getId()));
        } else {
            db = Meal.builder()
                    .label(m.getLabel())
                    .stay(stay)
                    .build();
        }

        db.setStay(stay);
        finalMeals.add(db);
    }
    stay.setMeals(finalMeals);

    // -------- LEARNING SKILLS --------
    List<LearningSkill> finalSkills = new ArrayList<>();
    for (LearningSkill s : stay.getLearningSkills()) {
        LearningSkill db;

        if (s.getId() != null) {
            db = skillRepo.findById(s.getId())
                    .orElseThrow(() -> new RuntimeException("Skill not found: " + s.getId()));
        } else {
            db = LearningSkill.builder()
                    .label(s.getLabel())
                    .stay(stay)
                    .build();
        }

        db.setStay(stay);
        finalSkills.add(db);
    }
    stay.setLearningSkills(finalSkills);

    // -------- ACCOMMODATIONS --------
    List<Accomodation> finalAcc = new ArrayList<>();
    for (Accomodation ac : stay.getAccomodations()) {
        Accomodation db;

        if (ac.getId() != null) {
            db = accomodationRepo.findById(ac.getId())
                    .orElseThrow(() -> new RuntimeException("Accommodation not found: " + ac.getId()));
        } else {
            db = Accomodation.builder()
                    .label(ac.getLabel())
                    .stay(stay)
                    .build();
        }

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
        }
        return stayRepository.save(stay);
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

    /**
     * Permit to find all stays around a point (latitude, longitude)
     * A limit is define in case of http request to limit the flux
     * 
     * @param region
     * @param stepLat
     * @param stepLon
     * @param limit
     * @return List of the stays in the region provided
     */
    public List<Stay> findStaysAroundStep(String region, double stepLat, double stepLon, int limit) {
        List<Stay> candidates = stayRepository.findByRegion(region);

        return candidates.stream()
                .sorted(Comparator.comparingDouble(stay -> {
                    if (stay.getLocalisation() == null || stay.getLocalisation().length < 2)
                        return Double.MAX_VALUE;

                    return GeoUtils.distance(
                            stepLat, stepLon,
                            stay.getLocalisation()[0],
                            stay.getLocalisation()[1]);
                }))
                .limit(limit)
                .toList();
    }

}
