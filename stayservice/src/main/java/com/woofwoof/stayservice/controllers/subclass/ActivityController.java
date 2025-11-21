package com.woofwoof.stayservice.entities.subclass;

import com.woofwoof.stayservice.models.subclass.Activity;
import com.woofwoof.stayservice.repositories.subclass.ActivityRepository;

import org.springframework.web.bind.annotation.*;
import java.util.List;



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