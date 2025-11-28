package com.woofwoof.woofplanner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woofwoof.woofplanner.dto.RoadTripPlan;
import com.woofwoof.woofplanner.dto.RoadTripRequest;
import com.woofwoof.woofplanner.service.WoofPlannerService;

@RestController
@RequestMapping("/planner")
public class WoofPlannerController {

    private final WoofPlannerService plannerService;

    public WoofPlannerController(WoofPlannerService plannerService) {
        this.plannerService = plannerService;
    }

    @PostMapping("/create")
    public ResponseEntity<RoadTripPlan> createPlan(@RequestBody RoadTripRequest request) {
        RoadTripPlan roadTrip = plannerService.planRoadTrip(request);
        return ResponseEntity.ok(roadTrip);
    }
}