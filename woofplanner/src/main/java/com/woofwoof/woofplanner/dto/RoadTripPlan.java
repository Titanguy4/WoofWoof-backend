package com.woofwoof.woofplanner.dto;

import java.util.List;

public record RoadTripPlan(
        List<StepProposal> stepProposals) {
}