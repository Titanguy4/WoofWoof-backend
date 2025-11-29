package com.woofwoof.bookingservice.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.woofwoof.bookingservice.entity.BookingStatus;

public record BookingDTO(
                Long id,
                Long stayId,
                UUID userId,
                LocalDate startRequestedDate,
                LocalDate endRequestedDate,
                BookingStatus status,
                String email,
                String number) {
}
