package com.woofwoof.stayservice.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        private final Random random = new Random();

        private final String[] stayTypes = {
                        "Ferme bio", "Vignoble", "Éco-construction", "Permaculture",
                        "Élevage", "Maraîchage", "Apiculture", "Fromagerie",
                        "Refuge animalier", "Jardin pédagogique"
        };

        private final String[] activities = {
                        "Soins aux animaux", "Jardinage bio", "Vendanges", "Construction paille",
                        "Permaculture", "Récolte", "Entretien des vignes", "Fabrication fromage",
                        "Apiculture", "Compostage", "Taille des arbres", "Maraîchage"
        };

        private final String[] skills = {
                        "Permaculture", "Viticulture", "Éco-construction", "Agriculture biologique",
                        "Apiculture", "Fromagerie", "Agroforesterie", "Jardinage naturel"
        };

        private final String[] meals = {
                        "Petit-déjeuner", "Déjeuner", "Dîner"
        };

        private final String[] accommodations = {
                        "Chambre partagée", "Chambre privée", "Tente", "Camping-car",
                        "Yourte", "Cabane"
        };

        // Villes et coordonnées précises pour chaque région
        private final CityData[] cities = {
                        // Bretagne
                        new CityData(48.390394, -4.486076), // Brest
                        new CityData(48.117266, -1.677793), // Rennes
                        new CityData(48.514610, -2.760847), // Saint-Brieuc
                        new CityData(47.658236, -2.760847), // Vannes
                        new CityData(48.575000, -3.150000), // Lannion

                        // Nouvelle-Aquitaine
                        new CityData(44.837789, -0.579180), // Bordeaux
                        new CityData(45.183920, 0.721650), // Périgueux
                        new CityData(45.648377, 0.156982), // Angoulême
                        new CityData(43.296482, -0.370797), // Pau
                        new CityData(45.833619, -0.580687), // Cognac
                        new CityData(43.483152, -1.514699), // Biarritz

                        // Occitanie
                        new CityData(43.604652, 1.444209), // Toulouse
                        new CityData(43.610769, 3.876716), // Montpellier
                        new CityData(43.212910, 2.353663), // Carcassonne
                        new CityData(43.836699, 4.360054), // Nîmes
                        new CityData(44.351430, 2.577180), // Rodez
                        new CityData(42.698611, 2.895000), // Perpignan

                        // Provence-Alpes-Côte d'Azur
                        new CityData(43.296482, 5.369780), // Marseille
                        new CityData(43.124228, 5.928000), // Toulon
                        new CityData(43.710173, 7.261953), // Nice
                        new CityData(43.949317, 4.805528), // Avignon
                        new CityData(43.529742, 5.447427), // Aix-en-Provence
                        new CityData(43.676675, 6.738538), // Grasse

                        // Auvergne-Rhône-Alpes
                        new CityData(45.764043, 4.835659), // Lyon
                        new CityData(45.188529, 5.724524), // Grenoble
                        new CityData(45.564601, 5.917974), // Chambéry
                        new CityData(46.195397, 6.235634), // Annecy
                        new CityData(45.439695, 4.387178), // Saint-Étienne
                        new CityData(45.917700, 6.128300), // Albertville

                        // Grand Est
                        new CityData(48.573405, 7.752111), // Strasbourg
                        new CityData(47.750839, 7.335888), // Mulhouse
                        new CityData(49.119309, 6.176009), // Metz
                        new CityData(48.692054, 6.184417), // Nancy
                        new CityData(48.579400, 7.750000), // Colmar
                        new CityData(49.258329, 4.031696), // Reims

                        // Hauts-de-France
                        new CityData(50.629250, 3.057256), // Lille
                        new CityData(50.291570, 2.779360), // Arras
                        new CityData(49.894067, 2.295753), // Amiens
                        new CityData(49.417816, 2.826145), // Beauvais
                        new CityData(50.724944, 3.160278), // Roubaix

                        // Normandie
                        new CityData(49.182863, -0.370679), // Caen
                        new CityData(49.115995, -1.087765), // Saint-Lô
                        new CityData(48.431926, 0.087836), // Alençon
                        new CityData(49.024200, 1.151236), // Évreux
                        new CityData(49.443232, 1.099971), // Rouen
                        new CityData(49.649200, 0.087200), // Le Havre

                        // Pays de la Loire
                        new CityData(47.218371, -1.553621), // Nantes
                        new CityData(47.478419, -0.563166), // Angers
                        new CityData(46.670374, -1.426412), // La Roche-sur-Yon
                        new CityData(48.006899, 0.199556), // Le Mans
                        new CityData(47.216671, -1.550000), // Saint-Nazaire

                        // Centre-Val de Loire
                        new CityData(47.902964, 1.909251), // Orléans
                        new CityData(47.394144, 0.688120), // Tours
                        new CityData(47.081012, 2.398782), // Bourges
                        new CityData(47.585601, 1.335248), // Blois
                        new CityData(47.353600, 0.692900), // Amboise

                        // Bourgogne-Franche-Comté
                        new CityData(47.322047, 5.041480), // Dijon
                        new CityData(46.806663, 4.832938), // Mâcon
                        new CityData(47.237829, 6.024054), // Besançon
                        new CityData(46.669717, 5.557550), // Lons-le-Saunier
                        new CityData(47.497500, 6.798300), // Belfort

                        // Corse
                        new CityData(41.919229, 8.738635), // Ajaccio
                        new CityData(42.697283, 9.450881), // Bastia
                        new CityData(41.595300, 9.340700), // Porto-Vecchio
                        new CityData(42.578889, 8.746667) // Calvi
        };

        @Override
        public void run(String... args) throws Exception {
                if (stayRepository.count() == 0) {
                        log.info("Seeding database with 150 stays...");
                        seedStays(150);
                        log.info("Database seeded successfully with {} stays!", stayRepository.count());
                } else {
                        log.info("Database already contains data, skipping seeding.");
                }
        }

        private void seedStays(int count) {
                List<Stay> stays = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                        // Choisir une ville aléatoire
                        CityData city = cities[random.nextInt(cities.length)];

                        // Ajouter une variation plus petite autour de la ville (rayon ~500m)
                        Double lat = city.lat + (random.nextDouble() - 0.5) * 0.005;
                        Double lon = city.lon + (random.nextDouble() - 0.5) * 0.005;

                        // Récupérer le département et la région via le service de géocodage
                        GeocodingService.LocationInfo locationInfo = geocodingService.getLocationInfo(lat, lon);

                        // Petit délai pour éviter de surcharger l'API Nominatim
                        try {
                                Thread.sleep(100);
                        } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                        }

                        String stayType = stayTypes[random.nextInt(stayTypes.length)];

                        Stay stay = Stay.builder()
                                        .title(stayType + " en " + locationInfo.getRegion())
                                        .description("Découvrez " + stayType.toLowerCase() + " dans le département de "
                                                        + locationInfo.getDepartment())
                                        .localisation(new Double[] { lat, lon })
                                        .department(locationInfo.getDepartment())
                                        .region(locationInfo.getRegion())
                                        .status(random.nextBoolean())
                                        .wooferId((long) (random.nextInt(50) + 1))
                                        .build();

                        int activityCount = random.nextInt(3) + 2;
                        for (int j = 0; j < activityCount; j++) {
                                Activity activity = Activity.builder()
                                                .label(activities[random.nextInt(activities.length)])
                                                .stay(stay)
                                                .build();
                                stay.addActivity(activity);
                        }

                        int skillCount = random.nextInt(2) + 1;
                        for (int j = 0; j < skillCount; j++) {
                                LearningSkill skill = LearningSkill.builder()
                                                .label(skills[random.nextInt(skills.length)])
                                                .stay(stay)
                                                .build();
                                stay.addLearningSkill(skill);
                        }

                        int mealCount = random.nextInt(3) + 1;
                        for (int j = 0; j < mealCount; j++) {
                                Meal meal = Meal.builder()
                                                .label(meals[j % meals.length])
                                                .stay(stay)
                                                .build();
                                stay.addMeal(meal);
                        }

                        Accomodation accommodation = Accomodation.builder()
                                        .label(accommodations[random.nextInt(accommodations.length)])
                                        .stay(stay)
                                        .build();
                        stay.addAccommodation(accommodation);

                        int reviewCount = random.nextInt(4);
                        for (int j = 0; j < reviewCount; j++) {
                                Review review = Review.builder()
                                                .rating((long) (random.nextInt(3) + 3))
                                                .stay(stay)
                                                .build();
                                stay.addReview(review);
                        }

                        stays.add(stay);

                        if ((i + 1) % 10 == 0) {
                                log.info("Generated {} stays...", i + 1);
                        }
                }

                stayRepository.saveAll(stays);
        }

        private static class CityData {
                double lat;
                double lon;

                CityData(double lat, double lon) {
                        this.lat = lat;
                        this.lon = lon;
                }
        }
}
