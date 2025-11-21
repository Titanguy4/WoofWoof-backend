package com.woofwoof.stayservice.entities;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.woofwoof.stayservice.repositories.StayRepository;
import com.woofwoof.stayservice.services.StayService;
import com.woofwoof.stayservice.models.Stay;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/stays")
public class StayController {

    private final StayRepository stayRepository;
    private final StayService stayService;

    public StayController(StayService stayService, StayRepository stayRepository) {
        this.stayService = stayService;
        this.stayRepository = stayRepository;
    }

    @GetMapping("/{id}")
    public Stay getStayById(@PathVariable Long id) {
        return stayService.getStayById(id);
    }

    @GetMapping
    public List<Stay> getAllStays() {
        return stayRepository.findAll();
    }

    @PostMapping
    public Stay createStay(@RequestBody Stay stay) {
        return stayService.createStay(stay);
    }
    
    @PutMapping("/{id}")
    public Stay updateStay(@RequestBody Stay updatedStay, @PathVariable Long id) {
        return stayService.updateStay(updatedStay);
    }
    
    @DeleteMapping("/{id}")
    public void deleteStay(@PathVariable Long id) {
        stayService.deleteStay(id);
    }
}