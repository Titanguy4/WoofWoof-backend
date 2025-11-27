package com.woofwoof.bookingservice.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long stayId;

    private Long userId;

    private LocalDate startRequestedDate;

    private LocalDate endRequestedDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private String email;

    private String phoneNumber;
}
