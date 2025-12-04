package com.woofwoof.stayservice.repositories.subclass;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woofwoof.stayservice.entities.subclass.Accomodation;

@Repository
public interface AccomodationRepository extends JpaRepository<Accomodation, Long> {
    List<Accomodation> findByStay_Id(Long id);
}
