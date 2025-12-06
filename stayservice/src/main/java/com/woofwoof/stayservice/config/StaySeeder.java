package com.woofwoof.stayservice.config;

import java.time.LocalDate;
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
import com.woofwoof.stayservice.services.GeocodingService.CityLocation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({ "default", "dev", "test", "docker" })
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

                // ✨ Nouveau : Création avec nom de ville
                createStayByCity(
                                "Wine Farm Experience",
                                "Experience life on a vineyard and help with grape harvesting.",
                                StayType.FARM,
                                "Bordeaux");

                Thread.sleep(1100); // Respect du rate limiting

                createStayByCity(
                                "Animal Farm Volunteer",
                                "Care for farm animals and learn sustainable practices.",
                                StayType.FARM,
                                "Caen");

                Thread.sleep(1100);

                createStayByCity(
                                "Dog Shelter Helper",
                                "Assist with dog care, training and enrichment.",
                                StayType.ANIMAL,
                                "Marseille");

                Thread.sleep(1100);

                createStayByCity(
                                "Wildlife Rescue Center",
                                "Help rehabilitate injured wildlife for safe release.",
                                StayType.ANIMAL,
                                "Pau");

                Thread.sleep(1100);

                createStayByCity(
                                "Beach Cleanup Volunteer",
                                "Join coastal preservation efforts and marine cleanup.",
                                StayType.ENVIRONMENTAL,
                                "Rennes");

                Thread.sleep(1100);

                createStayByCity(
                                "Art Workshop Assistant",
                                "Assist in creative workshops and support local culture.",
                                StayType.CULTURAL,
                                "Nice");

                log.info("Stays seeded successfully!");
        }

        // ✨ Nouvelle méthode avec nom de ville
        private void createStayByCity(String title, String description, StayType type, String cityName) {

                // Récupérer les infos de la ville via l'API
                CityLocation cityLocation = geocodingService.getCityLocation(cityName);

                if (cityLocation == null) {
                        log.warn("Could not find city: {}. Skipping stay creation.", cityName);
                        return;
                }

                Stay stay = Stay.builder()
                                .title(title)
                                .description(description)
                                .type(type)
                                .localisation(new Double[] {
                                                (double) cityLocation.getLat(),
                                                (double) cityLocation.getLon()
                                })
                                .department(cityLocation.getDepartment())
                                .region(cityLocation.getRegion())
                                .status(true)
                                .wooferId(UUID.fromString("77d98606-a1ab-4313-9cb6-88d1f188f8ee"))
                                .wooferName("LeDZ Sucré")
                                .build();

                // Ajouter accommodations
                var labels = new String[] {
                                "Shared housing", "AC", "Wifi", "Flexible schedule",
                                "All meals", "TV", "Hot water"
                };
                var acc = new ArrayList<Accomodation>();
                for (String l : labels) {
                        acc.add(Accomodation.builder().label(l).stay(stay).build());
                }
                stay.setAccomodations(acc);

                // Activities
                stay.addActivity(Activity.builder().label("General help").stay(stay).build());

                // Learning Skills
                stay.addLearningSkill(LearningSkill.builder().label("Local knowledge").stay(stay).build());

                // Meals
                stay.addMeal(Meal.builder().label("Breakfast").stay(stay).build());
                stay.addMeal(Meal.builder().label("Lunch").stay(stay).build());

                // Review
                // Reviews
                stay.addReview(Review.builder()
                                .name("Anna")
                                .country("Germany")
                                .rating(5L)
                                .date(java.sql.Date.valueOf(LocalDate.of(2024, 1, 12)))
                                .content("Amazing experience! The host was very welcoming and the tasks were enjoyable.")
                                .build());

                stay.addReview(Review.builder()
                                .name("Lucas")
                                .country("France")
                                .rating(4L)
                                .date(java.sql.Date.valueOf(LocalDate.of(2024, 2, 8)))
                                .content("Great stay overall. The food was delicious and the place was quiet.")
                                .build());

                stay.addReview(Review.builder()
                                .name("Maria")
                                .country("Spain")
                                .rating(5L)
                                .date(java.sql.Date.valueOf(LocalDate.of(2024, 3, 20)))
                                .content("Loved every moment! Learned a lot and met amazing people.")
                                .build());

                stayRepository.save(stay);

                log.info("Created stay '{}' in {} ({}, {})",
                                title, cityName, cityLocation.getDepartment(), cityLocation.getRegion());
        }
}
