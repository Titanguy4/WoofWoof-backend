package com.woofwoof.bookingservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.woofwoof.bookingservice.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStayId(Long stayId);
    List<Booking> findByUserId(UUID userId);
}
