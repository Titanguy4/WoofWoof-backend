package com.woofwoof.woofplanner.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record RoadTripRequest(
                @NotNull List<StepRequest> steps,
                int maxProposalsPerStep) {
}