package com.woofwoof.bookingservice.dto;

import java.time.LocalDate;

import com.woofwoof.bookingservice.entity.BookingStatus;

public record BookingDTO(
        Long id,
        Long stayId,
        Long userId,
        LocalDate startRequestedDate,
        LocalDate endRequestedDate,
        BookingStatus status,
        String email,
        String number) {
}
