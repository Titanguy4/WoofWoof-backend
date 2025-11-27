package com.woofwoof.woofplanner.dto;

import java.util.List;

public record StepProposal(
        String cityName,
        List<StayDTO> recommendedStays) {
}
