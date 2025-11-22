package com.woofwoof.stayservice.repositories.subclass;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woofwoof.stayservice.entities.subclass.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByStay_Id(Long id);
}
