package com.woofwoof.stayservice.entities.subclass;

import com.woofwoof.stayservice.models.subclass.Meal;
import com.woofwoof.stayservice.repositories.subclass.MealRepository;

import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/meals")
public class MealController {

    private final MealRepository mealRepository;

    public MealController(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @GetMapping
    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    @GetMapping("/{id}")
    public Meal getMealById(@PathVariable Long id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Meal not found"));
    }

    @PostMapping
    public Meal createMeal(@RequestBody Meal meal) {
        if (meal.getId() != null) {
            throw new IllegalArgumentException("New Meal cannot already have an ID");
        }
        return mealRepository.save(meal);
    }

    @PutMapping("/{id}")
    public Meal updateMeal(@PathVariable Long id, @RequestBody Meal meal) {
        if (!mealRepository.existsById(id)) {
            throw new IllegalArgumentException("Meal not found");
        }
        meal.setId(id);
        return mealRepository.save(meal);
    }

    @DeleteMapping("/{id}")
    public void deleteMeal(@PathVariable Long id) {
        if (!mealRepository.existsById(id)) {
            throw new IllegalArgumentException("Meal not found");
        }
        mealRepository.deleteById(id);
    }
}