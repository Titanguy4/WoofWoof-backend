package com.woofwoof.stayservice.repositories;

import com.woofwoof.stayservice.models.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByStay_IdStay(Long stayId);
}
