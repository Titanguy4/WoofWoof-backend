package com.woofwoof.stayservice.controllers.subclass;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woofwoof.stayservice.entities.subclass.Activity;
import com.woofwoof.stayservice.repositories.subclass.ActivityRepository;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityRepository activityRepository;

    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @GetMapping
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @GetMapping("/{id}")
    public Activity getActivityById(@PathVariable Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found"));
    }

    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        if (activity.getId() != null) {
            throw new IllegalArgumentException("New Activity cannot already have an ID");
        }
        return activityRepository.save(activity);
    }

    @PutMapping("/{id}")
    public Activity updateActivity(@PathVariable long id, @RequestBody Activity activity) {
        if (!activityRepository.existsById(id)) {
            throw new IllegalArgumentException("Activity not found");
        }
        activity.setId(id);
        return activityRepository.save(activity);
    }

    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable long id) {
        if (!activityRepository.existsById(id)) {
            throw new IllegalArgumentException("Activity not found");
        }
        activityRepository.deleteById(id);
    }
}