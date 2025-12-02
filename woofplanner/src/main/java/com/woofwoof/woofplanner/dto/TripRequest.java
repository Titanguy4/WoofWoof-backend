package com.woofwoof.woofplanner.dto;

import java.time.LocalDate;

public record TripRequest(
        String startLocation,
        String endLocation,
        LocalDate startDate,
        LocalDate endDate,
        int numberOfStops) {
}
