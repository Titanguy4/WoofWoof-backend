package com.woofwoof.stayservice.repositories;

import com.woofwoof.stayservice.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByStay_IdStay(Long stayId);
}
