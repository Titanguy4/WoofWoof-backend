package com.woofwoof.stayservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woofwoof.stayservice.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByStay_Id(Long id);
}
