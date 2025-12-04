package com.woofwoof.bookingservice.dto.mapper;

import org.springframework.stereotype.Component;

import com.woofwoof.bookingservice.dto.BookingCreateDTO;
import com.woofwoof.bookingservice.dto.BookingDTO;
import com.woofwoof.bookingservice.entity.Booking;
import com.woofwoof.bookingservice.entity.BookingStatus;

@Component
public class BookingMapper {
    public BookingDTO toDto(Booking entity) {
        if (entity == null) {
            return null;
        }

        return new BookingDTO(
                entity.getId(),
                entity.getStayId(),
                entity.getUserId(),
                entity.getStartRequestedDate(),
                entity.getEndRequestedDate(),
                entity.getStatus(),
                entity.getEmail(),
                entity.getPhoneNumber());
    }

    public Booking toEntity(BookingCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        Booking booking = new Booking();

        // Default value for status
        if (dto.status() != null && !dto.status().isBlank()) {
            try {
                booking.setStatus(BookingStatus.valueOf(dto.status().toUpperCase()));
            } catch (IllegalArgumentException e) {
                booking.setStatus(BookingStatus.PENDING);
            }
        } else {
            booking.setStatus(BookingStatus.PENDING);
        }

        booking.setStayId(dto.stayId());
        booking.setUserId(dto.userId());
        booking.setStartRequestedDate(dto.startRequestedDate());
        booking.setEndRequestedDate(dto.endRequestedDate());
        booking.setEmail(dto.email());
        booking.setPhoneNumber(dto.number());

        return booking;
    }

}
