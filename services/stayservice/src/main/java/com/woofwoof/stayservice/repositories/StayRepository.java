package com.woofwoof.stayservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woofwoof.stayservice.models.Stay;

@Repository
public interface StayRepository extends JpaRepository <Stay, Stay> {
    
}
