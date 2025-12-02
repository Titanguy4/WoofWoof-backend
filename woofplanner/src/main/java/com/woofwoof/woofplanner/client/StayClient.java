package com.woofwoof.woofplanner.client;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.woofwoof.woofplanner.dto.StayDTO;

@HttpExchange("/stays")
public interface StayClient {

    @GetExchange("/search/proximity")
    List<StayDTO> findStaysWithProximity(
            @RequestParam("region") String region,
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon,
            @RequestParam("limit") int limit);
}
