package com.woofwoof.woofplanner.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.woofwoof.woofplanner.client.StayClient;
import com.woofwoof.woofplanner.dto.RoadTripPlan;
import com.woofwoof.woofplanner.dto.RoadTripRequest;
import com.woofwoof.woofplanner.dto.StayDTO;
import com.woofwoof.woofplanner.dto.StepProposal;
import com.woofwoof.woofplanner.dto.StepRequest;

@Service
public class WoofPlannerService {

    private final StayClient stayClient;

    public WoofPlannerService(StayClient stayClient) {
        this.stayClient = stayClient;
    }

    public RoadTripPlan planRoadTrip(RoadTripRequest request) {

        int maxProposalsPerStep = 3;
        if (request.maxProposalsPerStep() > 0) {
            maxProposalsPerStep = request.maxProposalsPerStep();
        }

        List<StepProposal> proposals = new ArrayList<>();

        for (StepRequest step : request.steps()) {

            List<StayDTO> candidates = stayClient.findStaysWithProximity(
                    step.region(),
                    step.latitude(),
                    step.longitude(),
                    maxProposalsPerStep);

            proposals.add(new StepProposal(step.cityName(), candidates));
        }

        return new RoadTripPlan(proposals);
    }
}
