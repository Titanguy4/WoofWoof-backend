package com.woofwoof.bookingservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.woofwoof.bookingservice.dto.BookingCreateDTO;
import com.woofwoof.bookingservice.dto.BookingDTO;
import com.woofwoof.bookingservice.dto.mapper.BookingMapper;
import com.woofwoof.bookingservice.entity.Booking;
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

    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return bMapper.toDto(booking);
    }

    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(bMapper::toDto).toList();
    }

    public BookingDTO createBooking(BookingCreateDTO booking) {
        if (!booking.endRequestedDate().isAfter(booking.startRequestedDate())) {
            throw new IllegalArgumentException("La date de fin doit être après la date de début");
        }

        // todo: implémenter le fait que si un stay n'est pas disponible (status false)
        // alors throw BookingUnavailableException("Ce logement est déjà réservé pour
        // ces dates.")

        Booking entity = bMapper.toEntity(booking);
        Booking entitySaved = bookingRepository.save(entity);
        return bMapper.toDto(entitySaved);
    }
}
