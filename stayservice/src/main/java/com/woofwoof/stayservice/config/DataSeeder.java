package com.woofwoof.stayservice.config;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.woofwoof.stayservice.entities.Review;
import com.woofwoof.stayservice.entities.Stay;
import com.woofwoof.stayservice.entities.StayType;
import com.woofwoof.stayservice.entities.subclass.Accomodation;
import com.woofwoof.stayservice.entities.subclass.Activity;
import com.woofwoof.stayservice.entities.subclass.LearningSkill;
import com.woofwoof.stayservice.entities.subclass.Meal;
import com.woofwoof.stayservice.repositories.StayRepository;
import com.woofwoof.stayservice.services.GeocodingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({ "dev", "test" })
public class DataSeeder implements CommandLineRunner {

        private final StayRepository stayRepository;
        private final GeocodingService geocodingService;

        @Override
        public void run(String... args) throws Exception {

                if (stayRepository.count() > 0) {
                        log.info("Database already contains data. Skipping seeding.");
                        return;
                }

                log.info("Seeding stays...");

                createStay(
                                "Wine Farm Experience",
                                "Experience life on a vineyard and help with grape harvesting.",
                                StayType.FARM,
                                new Long[] { 44449000L, 144000L });

                createStay(
                                "Animal Farm Volunteer",
                                "Care for farm animals and learn sustainable practices.",
                                StayType.FARM,
                                new Long[] { 49182900L, -370700L });

                createStay(
                                "Dog Shelter Helper",
                                "Assist with dog care, training and enrichment.",
                                StayType.ANIMAL,
                                new Long[] { 43296500L, 5369800L });

                createStay(
                                "Wildlife Rescue Center",
                                "Help rehabilitate injured wildlife for safe release.",
                                StayType.ANIMAL,
                                new Long[] { 42826300L, -6400L });

                createStay(
                                "Beach Cleanup Volunteer",
                                "Join coastal preservation efforts and marine cleanup.",
                                StayType.ENVIRONMENTAL,
                                new Long[] { 48117300L, -1677800L });

                createStay(
                                "Art Workshop Assistant",
                                "Assist in creative workshops and support local culture.",
                                StayType.CULTURAL,
                                new Long[] { 43710200L, 726200L });

                log.info("Stays seeded successfully!");
        }

        private void createStay(String title, String description, StayType type, Long[] localisation) {

                Stay stay = Stay.builder()
                                .title(title)
                                .description(description)
                                .type(type)
                                .localisation(localisation)
                                .status(true)
                                .wooferId(UUID.randomUUID()) // 👈 UUID OK ici
                                .build();

                // ➤ Ajouter department & region via GeocodingService
                applyLocationInfo(stay);

                // ➤ Ajouter accommodations
                var labels = new String[] {
                                "Shared housing", "AC", "Wifi", "Flexible schedule",
                                "All meals", "TV", "Hot water"
                };
                var acc = new ArrayList<Accomodation>();
                for (String l : labels) {
                        acc.add(Accomodation.builder().label(l).stay(stay).build());
                }
                stay.setAccomodations(acc);

                // ➤ Activities
                stay.addActivity(Activity.builder().label("General help").stay(stay).build());

                // ➤ Learning Skills
                stay.addLearningSkill(LearningSkill.builder().label("Local knowledge").stay(stay).build());

                // ➤ Meals
                stay.addMeal(Meal.builder().label("Breakfast").stay(stay).build());
                stay.addMeal(Meal.builder().label("Lunch").stay(stay).build());

                // ➤ Review
                stay.addReview(Review.builder().rating(5L).stay(stay).build());

                stayRepository.save(stay);
        }

        private void applyLocationInfo(Stay stay) {
                // Coordinates are stored in microdegrees => divide
                double lat = stay.getLocalisation()[0] / 1_000_000.0;
                double lon = stay.getLocalisation()[1] / 1_000_000.0;

                var info = geocodingService.getLocationInfo(lat, lon);

                stay.setDepartment(info.getDepartment());
                stay.setRegion(info.getRegion());
        }
}
