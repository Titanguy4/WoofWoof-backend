package com.woofwoof.stayservice.repositories;

import java.util.List;
import java.util.UUID;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woofwoof.stayservice.entities.Stay;

@Repository
public interface StayRepository extends JpaRepository<Stay, Long> {

    List<Stay> findByRegion(String region);

    List<Stay> findByDepartment(String department);

    List<Stay> findByLocalisation(Long[] localisation);

    List<Stay> findByStatus(Boolean status);

    List<Stay> findByWooferId(UUID wooferId);

    boolean existsByWooferId(UUID wooferId);

}
