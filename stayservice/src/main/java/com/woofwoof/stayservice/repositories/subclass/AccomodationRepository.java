package com.woofwoof.stayservice.repositories.subclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woofwoof.stayservice.models.subclass.Accomodation;

import java.util.List;

@Repository
public interface AccomodationRepository extends JpaRepository<Accomodation, Long> {
    List<Accomodation> findByStay_Id(Long id);
}
