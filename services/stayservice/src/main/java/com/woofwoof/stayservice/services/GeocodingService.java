package com.woofwoof.stayservice.services;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingService {

    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/reverse?format=json";

    public LocationInfo getLocationInfo(double lat, double lon) {
        try {
            String url = NOMINATIM_URL + "&lat=" + lat + "&lon=" + lon + "&zoom=10&addressdetails=1";
            RestTemplate restTemplate = new RestTemplate();
            String json = restTemplate.getForObject(url, String.class);

            JSONObject obj = new JSONObject(json);
            JSONObject address = obj.getJSONObject("address");

            String department = null;
            String region = null;

            if (address.has("county")) department = address.getString("county");
            else if (address.has("state_district")) department = address.getString("state_district");

            if (address.has("state")) region = address.getString("state");
            else if (address.has("region")) region = address.getString("region");

            if (department == null) department = "Unknown";
            if (region == null) region = "Unknown";

            return new LocationInfo(department, region);

        } catch (Exception e) {
            return new LocationInfo("Unknown", "Unknown");
        }
    }

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
}
