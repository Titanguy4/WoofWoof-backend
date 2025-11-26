package com.woofwoof.stayservice.config;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.woofwoof.stayservice.entities.Review;
import com.woofwoof.stayservice.entities.Stay;
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

                // Activités pour la ferme bretonne
                Activity activity1 = Activity.builder()
                                .label("Soins aux animaux")
                                .stay(stayBretagne)
                                .build();

                Activity activity2 = Activity.builder()
                                .label("Jardinage bio")
                                .stay(stayBretagne)
                                .build();

                stayBretagne.addActivity(activity1);
                stayBretagne.addActivity(activity2);

                // Compétences d'apprentissage
                LearningSkill skill1 = LearningSkill.builder()
                                .label("Permaculture")
                                .stay(stayBretagne)
                                .build();

                stayBretagne.addLearningSkill(skill1);

                // Repas
                Meal meal1 = Meal.builder()
                                .label("Petit-déjeuner")
                                .stay(stayBretagne)
                                .build();

                Meal meal2 = Meal.builder()
                                .label("Déjeuner")
                                .stay(stayBretagne)
                                .build();

                stayBretagne.addMeal(meal1);
                stayBretagne.addMeal(meal2);

                // Hébergement
                Accomodation accommodation1 = Accomodation.builder()
                                .label("Chambre partagée")
                                .stay(stayBretagne)
                                .build();

                stayBretagne.addAccommodation(accommodation1);

                // Review
                Review review1 = Review.builder()
                                .rating(Long.valueOf(5))
                                .stay(stayBretagne)
                                .build();

                stayBretagne.addReview(review1);

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

                Activity activity3 = Activity.builder()
                                .label("Vendanges")
                                .stay(stayProvence)
                                .build();

                Activity activity4 = Activity.builder()
                                .label("Entretien des vignes")
                                .stay(stayProvence)
                                .build();

                stayProvence.addActivity(activity3);
                stayProvence.addActivity(activity4);

                LearningSkill skill2 = LearningSkill.builder()
                                .label("Viticulture")
                                .stay(stayProvence)
                                .build();

                stayProvence.addLearningSkill(skill2);

                Meal meal3 = Meal.builder()
                                .label("Déjeuner")
                                .stay(stayProvence)
                                .build();

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
        }
}