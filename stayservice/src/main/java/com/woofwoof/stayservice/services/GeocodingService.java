package com.woofwoof.stayservice.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GeocodingService {

    private static final String REVERSE_URL = "https://nominatim.openstreetmap.org/reverse?format=json";
    private static final String SEARCH_URL = "https://nominatim.openstreetmap.org/search?format=json&addressdetails=1&limit=1&countrycodes=fr";

    /**
     * Méthode existante : Lat/Lon -> Adresse (Département/Région)
     */
    public LocationInfo getLocationInfo(double lat, double lon) {
        try {
            String url = REVERSE_URL + "&lat=" + lat + "&lon=" + lon + "&zoom=10&addressdetails=1";
            RestTemplate restTemplate = new RestTemplate();
            String json = restTemplate.getForObject(url, String.class);

            JSONObject obj = new JSONObject(json);
            JSONObject address = obj.getJSONObject("address");

            return extractLocationData(address);

        } catch (Exception e) {
            log.error("Error in reverse geocoding: {}", e.getMessage());
            return new LocationInfo("Unknown", "Unknown");
        }
    }

    /**
     * Nom de ville -> Lat/Lon + Département/Région
     */
    public CityLocation getCityLocation(String cityName) {
        try {
            String url = SEARCH_URL + "&q=" + cityName;
            RestTemplate restTemplate = new RestTemplate();

            // L'API Search renvoie un TABLEAU (JSONArray), contrairement au reverse qui
            // renvoie un OBJET
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JSONArray jsonArray = new JSONArray(jsonResponse);

            if (jsonArray.isEmpty()) {
                log.warn("City not found: {}", cityName);
                return null;
            }

            // On prend le premier résultat
            JSONObject firstResult = jsonArray.getJSONObject(0);

            // Récupération coordonnées
            double lat = firstResult.getDouble("lat");
            double lon = firstResult.getDouble("lon");

            // Récupération adresse
            JSONObject address = firstResult.getJSONObject("address");
            LocationInfo locInfo = extractLocationData(address);

            return new CityLocation(lat, lon, locInfo.getDepartment(), locInfo.getRegion());

        } catch (Exception e) {
            log.error("Error finding city {}: {}", cityName, e.getMessage());
            return null;
        }
    }

    // Méthode utilitaire pour éviter de dupliquer la logique d'extraction JSON
    private LocationInfo extractLocationData(JSONObject address) {
        String department = null;
        String region = null;

        if (address.has("county"))
            department = address.getString("county");
        else if (address.has("state_district"))
            department = address.getString("state_district");

        if (address.has("state"))
            region = address.getString("state");
        else if (address.has("region"))
            region = address.getString("region");

        if (department == null)
            department = "Unknown";
        if (region == null)
            region = "Unknown";

        return new LocationInfo(department, region);
    }

    // --- DTOs ---

    // Ton ancienne classe (inchangée)
    public static class LocationInfo {
        private final String department;
        private final String region;

        public LocationInfo(String department, String region) {
            this.department = department;
            this.region = region;
        }

        public String getDepartment() {
            return department;
        }

        public String getRegion() {
            return region;
        }
    }

    // Nouvelle classe qui contient tout (pour le Seeder)
    @Getter
    @AllArgsConstructor
    public static class CityLocation {
        private final double lat;
        private final double lon;
        private final String department;
        private final String region;
    }
}