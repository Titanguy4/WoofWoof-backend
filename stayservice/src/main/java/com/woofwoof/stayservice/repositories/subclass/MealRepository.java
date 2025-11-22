package com.woofwoof.stayservice.repositories.subclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woofwoof.stayservice.models.subclass.Meal;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByStay_Id(Long id);
}
