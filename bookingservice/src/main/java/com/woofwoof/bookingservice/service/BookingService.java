package com.woofwoof.bookingservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.woofwoof.bookingservice.dto.BookingCreateDTO;
import com.woofwoof.bookingservice.dto.BookingDTO;
import com.woofwoof.bookingservice.dto.mapper.BookingMapper;
import com.woofwoof.bookingservice.entity.Booking;
import com.woofwoof.bookingservice.entity.BookingStatus;
import com.woofwoof.bookingservice.repository.BookingRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bMapper;

    public BookingService(BookingRepository bookingRepository, BookingMapper bMapper) {
        this.bookingRepository = bookingRepository;
        this.bMapper = bMapper;
    }

    /** GET booking by ID */
    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id " + id));
        return bMapper.toDto(booking);
    }

    /** GET all bookings */
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bMapper::toDto)
                .toList();
    }

    /** CREATE booking */
    public BookingDTO createBooking(BookingCreateDTO booking) {
        if (!booking.endRequestedDate().isAfter(booking.startRequestedDate())) {
            throw new IllegalArgumentException("La date de fin doit être après la date de début");
        }

        Booking entity = bMapper.toEntity(booking);
        Booking saved = bookingRepository.save(entity);
        return bMapper.toDto(saved);
    }

    /** GET bookings by stayId */
    public List<BookingDTO> getBookingsByStayId(Long stayId) {
        return bookingRepository.findByStayId(stayId).stream()
                .map(bMapper::toDto)
                .toList();
    }

    /** GET bookings by userId */
    public List<BookingDTO> getBookingsByUserId(UUID userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(bMapper::toDto)
                .toList();
    }

    /** UPDATE booking status (ACCEPTED / REJECTED) */
    public BookingDTO updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id " + id));
        booking.setStatus(status);
        Booking saved = bookingRepository.save(booking);
        return bMapper.toDto(saved);
    }
}
