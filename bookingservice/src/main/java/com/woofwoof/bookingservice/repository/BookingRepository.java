package com.woofwoof.bookingservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.woofwoof.bookingservice.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStayId(Long stayId);
}
