package com.woofwoof.stayservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.woofwoof.stayservice.models.Accomodation;
import com.woofwoof.stayservice.models.Activity;
import com.woofwoof.stayservice.models.LearningSkill;
import com.woofwoof.stayservice.models.Meal;
import com.woofwoof.stayservice.models.Review;

import com.woofwoof.stayservice.models.Stay;

@Repository
public interface StayRepository extends JpaRepository<Stay, Long> {

    List<Meal> findMealsByStayId(Long stayId);

    List<Accomodation> findAccommodationsByStayId(Long stayId);

    List<Activity> findActivitiesByStayId(Long stayId);

    List<LearningSkill> findLearningSkillsByStayId(Long stayId);

    Stay findByName(String name);

    List<Stay> findByLocation(String location);

    List<Stay> findByStatus(Boolean status);

    Stay findByWooferId(Long wooferId);

    Stay findByBookingId(Long bookingId);

    List<Stay> findByStartDateAfter(java.util.Date startDate);

    List<Stay> findByEndDateBefore(java.util.Date endDate);

    List<Review> findReviewsByStayId(Long stayId);
}
