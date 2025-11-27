package com.woofwoof.woofplanner.dto;

import java.util.List;

public record RoadTripRequest(
        List<StepRequest> steps,
        int maxProposalsPerStep) {
}