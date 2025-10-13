package com.woofwoof.stayservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woofwoof.stayservice.models.Stayservice;

@Repository
public interface StayserviceRepository extends JpaRepository <Stayservice, Stayservice> {
    
}
