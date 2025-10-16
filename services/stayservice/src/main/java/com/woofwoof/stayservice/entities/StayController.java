package com.woofwoof.stayservice.entities;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woofwoof.stayservice.services.StayService;
import com.woofwoof.stayservice.models.Stay;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/stayservice")
public class StayController {
    private final StayService stayService;

    public StayController(StayService stayService) {
        this.stayService = stayService;
    }

    @GetMapping("/{id}")
    public Optional<Stay> getStayById(@PathVariable Long id) {
        return stayService.getStayById(id);
    }

    @GetMapping
    public List<Stay> getAllStays() {
        return stayService.getAllStays();
    }

    @PostMapping
    public Stay createStay(@RequestBody Stay stay) {
        return stayService.createStay(stay);
    }
    
    @PutMapping("/{id}")
    public Stay updateStay(@RequestBody Stay updatedStay) {
        return stayService.updateStay(updatedStay);
    }
    
    @DeleteMapping("/{id}")
    public void deleteStay(@PathVariable Long id) {
        stayService.deleteStay(id);
    }
}