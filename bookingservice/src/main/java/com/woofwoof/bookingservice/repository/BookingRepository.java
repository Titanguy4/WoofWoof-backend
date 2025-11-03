package com.woofwoof.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.woofwoof.bookingservice.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
