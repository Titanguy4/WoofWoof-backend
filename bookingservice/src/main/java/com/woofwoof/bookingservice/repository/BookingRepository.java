package com.woofwoof.bookingservice.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.woofwoof.bookingservice.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
            FROM Booking b
            WHERE b.stayId = :stayId
            AND (
                (b.startRequestedDate <= :endDate AND b.endRequestedDate >= :startDate)
            )
            """)
    Boolean existsByStayIdAndDatesOverlap(
            @Param("stayId") Long stayId,
            @Param("startDate") LocalDate start,
            @Param("endDate") LocalDate end);

    List<Booking> findByStayId(Long stayId);
    List<Booking> findByUserId(UUID userId);
}
