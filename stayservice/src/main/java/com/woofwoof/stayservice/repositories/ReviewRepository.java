package com.woofwoof.stayservice.repositories;

import com.woofwoof.stayservice.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByStay_Id(Long id);
}
