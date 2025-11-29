package com.woofwoof.stayservice.config;

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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({ "dev", "test" })
public class DataSeeder implements CommandLineRunner {

    private final StayRepository stayRepository;

    @Override
    public void run(String... args) throws Exception {
        if (stayRepository.count() == 0) {
            log.info("Seeding database with test data...");
            seedStays();
            log.info("Database seeded successfully!");
        } else {
            log.info("Database already contains data, skipping seeding.");
        }
    }

<<<<<<< HEAD
        private void seedStays() {
                // Séjour 1: Ferme bio en Bretagne
                Stay stayBretagne = Stay.builder()
                                .title("Ferme bio en Bretagne")
                                .description("Découvrez l'agriculture biologique dans une ferme familiale au cœur de la Bretagne")
                                .localisation(new Long[] { 48117300L, -1677800L })
                                .department("Ille-et-Vilaine")
                                .region("Bretagne")
                                .status(true)
                                .wooferId(UUID.randomUUID())
                                .build();
=======
    private void seedStays() {
>>>>>>> dev

        // ----------------- Stay 1 -----------------
        Stay stay1 = Stay.builder()
                .title("Wine Farm Experience")
                .description("Experience life on a traditional vineyard, help with grape harvesting and learn about winemaking.")
                .type(StayType.FARM)
                .localisation(new Long[]{44449000L, 144000L})
                .status(true)
                .wooferId(1L)
                .build();

        stay1.setAccomodations(createDefaultAccomodations(stay1, 1));
        stay1.addActivity(Activity.builder().label("Grape harvesting").stay(stay1).build());
        stay1.addLearningSkill(LearningSkill.builder().label("Winemaking").stay(stay1).build());
        stay1.addMeal(Meal.builder().label("Breakfast").stay(stay1).build());
        stay1.addMeal(Meal.builder().label("Lunch").stay(stay1).build());
        stay1.addReview(Review.builder().rating(5L).stay(stay1).build());

        stayRepository.save(stay1);

        // ----------------- Stay 2 -----------------
        Stay stay2 = Stay.builder()
                .title("Animal Farm Volunteer")
                .description("Help care for farm animals, feed them, and learn about sustainable farming practices.")
                .type(StayType.FARM)
                .localisation(new Long[]{49182900L, -370700L})
                .status(true)
                .wooferId(2L)
                .build();

        stay2.setAccomodations(createDefaultAccomodations(stay2, 8));
        stay2.addActivity(Activity.builder().label("Animal feeding").stay(stay2).build());
        stay2.addLearningSkill(LearningSkill.builder().label("Animal care").stay(stay2).build());
        stay2.addMeal(Meal.builder().label("Breakfast").stay(stay2).build());
        stay2.addMeal(Meal.builder().label("Lunch").stay(stay2).build());
        stay2.addReview(Review.builder().rating(4L).stay(stay2).build());

        stayRepository.save(stay2);

        // ----------------- Stay 3 -----------------
        Stay stay3 = Stay.builder()
                .title("Dog Shelter Helper")
                .description("Assist in caring for dogs, help with training sessions, and provide love and attention to the animals.")
                .type(StayType.ANIMAL)
                .localisation(new Long[]{43296500L, 5369800L})
                .status(true)
                .wooferId(3L)
                .build();

        stay3.setAccomodations(createDefaultAccomodations(stay3, 15));
        stay3.addActivity(Activity.builder().label("Dog training").stay(stay3).build());
        stay3.addLearningSkill(LearningSkill.builder().label("Animal behavior").stay(stay3).build());
        stay3.addMeal(Meal.builder().label("Breakfast").stay(stay3).build());
        stay3.addMeal(Meal.builder().label("Lunch").stay(stay3).build());
        stay3.addReview(Review.builder().rating(5L).stay(stay3).build());

        stayRepository.save(stay3);

        // ----------------- Stay 4 -----------------
        Stay stay4 = Stay.builder()
                .title("Wildlife Rescue Center")
                .description("Join our team to care for injured wildlife and participate in rehabilitation programs for safe release.")
                .type(StayType.ANIMAL)
                .localisation(new Long[]{42826300L, -6400L})
                .status(true)
                .wooferId(4L)
                .build();

        stay4.setAccomodations(createDefaultAccomodations(stay4, 22));
        stay4.addActivity(Activity.builder().label("Wildlife care").stay(stay4).build());
        stay4.addLearningSkill(LearningSkill.builder().label("Wildlife rehabilitation").stay(stay4).build());
        stay4.addMeal(Meal.builder().label("Breakfast").stay(stay4).build());
        stay4.addMeal(Meal.builder().label("Lunch").stay(stay4).build());
        stay4.addReview(Review.builder().rating(4L).stay(stay4).build());

        stayRepository.save(stay4);

<<<<<<< HEAD
                // Séjour 2: Vignoble en Provence
                Stay stayProvence = Stay.builder()
                                .title("Vignoble familial en Provence")
                                .description("Participez aux vendanges et découvrez les secrets de la viticulture provençale")
                                .localisation(new Long[] { 43296482L, 5369780L })
                                .department("Bouches-du-Rhône")
                                .region("Provence-Alpes-Côte d'Azur")
                                .status(true)
                                .wooferId(UUID.randomUUID())
                                .build();
=======
        // ----------------- Stay 5 -----------------
        Stay stay5 = Stay.builder()
                .title("Beach Cleanup Volunteer")
                .description("Help preserve the coastline by participating in organized beach cleanups and marine protection activities.")
                .type(StayType.ENVIRONMENTAL)
                .localisation(new Long[]{48117300L, -1677800L})
                .status(true)
                .wooferId(5L)
                .build();
>>>>>>> dev

        stay5.setAccomodations(createDefaultAccomodations(stay5, 29));
        stay5.addActivity(Activity.builder().label("Beach cleanup").stay(stay5).build());
        stay5.addLearningSkill(LearningSkill.builder().label("Environmental conservation").stay(stay5).build());
        stay5.addMeal(Meal.builder().label("Breakfast").stay(stay5).build());
        stay5.addMeal(Meal.builder().label("Lunch").stay(stay5).build());
        stay5.addReview(Review.builder().rating(5L).stay(stay5).build());

        stayRepository.save(stay5);

        // ----------------- Stay 6 -----------------
        Stay stay6 = Stay.builder()
                .title("Art Workshop Assistant")
                .description("Support local art initiatives by assisting in workshops, engaging with children, and promoting cultural heritage.")
                .type(StayType.CULTURAL)
                .localisation(new Long[]{43710200L, 726200L})
                .status(true)
                .wooferId(6L)
                .build();

        stay6.setAccomodations(createDefaultAccomodations(stay6, 36));
        stay6.addActivity(Activity.builder().label("Art workshop assistance").stay(stay6).build());
        stay6.addLearningSkill(LearningSkill.builder().label("Arts & crafts").stay(stay6).build());
        stay6.addMeal(Meal.builder().label("Breakfast").stay(stay6).build());
        stay6.addMeal(Meal.builder().label("Lunch").stay(stay6).build());
        stay6.addReview(Review.builder().rating(4L).stay(stay6).build());

        stayRepository.save(stay6);

        log.info("Created 6 stays with activities, learning skills, meals, accommodations, and reviews.");
    }

<<<<<<< HEAD
                stayProvence.addMeal(meal3);

                Accomodation accommodation2 = Accomodation.builder()
                                .label("Chambre privée")
                                .stay(stayProvence)
                                .build();

                stayProvence.addAccommodation(accommodation2);

                // Séjour 3: Éco-construction dans les Alpes
                Stay stayAlpes = Stay.builder()
                                .title("Éco-construction en montagne")
                                .description("Apprenez les techniques de construction écologique dans un cadre montagnard")
                                .localisation(new Long[] { 45188529L, 5724524L })
                                .department("Isère")
                                .region("Auvergne-Rhône-Alpes")
                                .status(false)
                                .wooferId(UUID.randomUUID())
                                .build();

                Activity activity5 = Activity.builder()
                                .label("Construction paille")
                                .stay(stayAlpes)
                                .build();

                stayAlpes.addActivity(activity5);

                LearningSkill skill3 = LearningSkill.builder()
                                .label("Éco-construction")
                                .stay(stayAlpes)
                                .build();

                stayAlpes.addLearningSkill(skill3);

                // Sauvegarder tous les séjours
                stayRepository.save(stayBretagne);
                stayRepository.save(stayProvence);
                stayRepository.save(stayAlpes);

                log.info("Created {} stays with associated data", 3);
=======
    private java.util.List<Accomodation> createDefaultAccomodations(Stay stay, long startId) {
        java.util.List<Accomodation> list = new java.util.ArrayList<>();
        String[] labels = { "Shared housing", "AC", "Wifi", "Flexible schedule", "All meals", "TV", "Hot water" };
        for (int i = 0; i < labels.length; i++) {
            Accomodation a = Accomodation.builder()
                    .label(labels[i])
                    .stay(stay)
                    .build();
            list.add(a);
>>>>>>> dev
        }
        return list;
    }
}
