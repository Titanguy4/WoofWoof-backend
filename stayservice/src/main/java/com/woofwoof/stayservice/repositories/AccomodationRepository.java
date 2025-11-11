package com.woofwoof.stayservice.repositories;

import com.woofwoof.stayservice.models.Accomodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccomodationRepository extends JpaRepository<Accomodation, Long> {
    List<Accomodation> findByStay_IdStay(Long stayId);
}
