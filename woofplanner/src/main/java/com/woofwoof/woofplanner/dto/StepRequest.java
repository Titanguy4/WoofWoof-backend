package com.woofwoof.woofplanner.dto;

public record StepRequest(String cityName,
        String region,
        double latitude,
        double longitude) {

}
