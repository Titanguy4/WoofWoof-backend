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
@Profile({ "default", "dev", "test" })
public class StaySeeder implements CommandLineRunner {

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
                                44.449000, 0.144000);

                createStay(
                                "Animal Farm Volunteer",
                                "Care for farm animals and learn sustainable practices.",
                                StayType.FARM,
                                49.182900, -0.370700);

                createStay(
                                "Dog Shelter Helper",
                                "Assist with dog care, training and enrichment.",
                                StayType.ANIMAL,
                                43.296500, 5.369800);

                createStay(
                                "Wildlife Rescue Center",
                                "Help rehabilitate injured wildlife for safe release.",
                                StayType.ANIMAL,
                                42.826300, -0.006400);

                createStay(
                                "Beach Cleanup Volunteer",
                                "Join coastal preservation efforts and marine cleanup.",
                                StayType.ENVIRONMENTAL,
                                48.117300, -1.677800);

                createStay(
                                "Art Workshop Assistant",
                                "Assist in creative workshops and support local culture.",
                                StayType.CULTURAL,
                                43.710200, 7.262200);

                log.info("Stays seeded successfully!");
        }

        private void createStay(String title, String description, StayType type, double latitude, double longitude) {

                Stay stay = Stay.builder()
                                .title(title)
                                .description(description)
                                .type(type)
                                .localisation(new Double[] { (double) ((long) (latitude * 1_000_000.0)),
                                                (double) ((long) (longitude * 1_000_000.0)) })
                                .status(true)
                                .wooferId(UUID.randomUUID())
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
                double lat = stay.getLocalisation()[0] / 1_000_000.0;
                double lon = stay.getLocalisation()[1] / 1_000_000.0;

                var info = geocodingService.getLocationInfo(lat, lon);

                stay.setDepartment(info.getDepartment());
                stay.setRegion(info.getRegion());
        }
}
