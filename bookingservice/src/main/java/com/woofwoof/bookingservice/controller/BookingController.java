package com.woofwoof.bookingservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woofwoof.bookingservice.dto.BookingCreateDTO;
import com.woofwoof.bookingservice.dto.BookingDTO;
import com.woofwoof.bookingservice.entity.Booking;
import com.woofwoof.bookingservice.entity.BookingStatus;
import com.woofwoof.bookingservice.repository.BookingRepository;
import com.woofwoof.bookingservice.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private BookingService bookingService;
    private BookingRepository bookingRepository;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        BookingDTO booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody @Validated BookingCreateDTO booking) {
        BookingDTO bookingDto = bookingService.createBooking(booking);
        return ResponseEntity.ok(bookingDto);
    }

    @GetMapping("/stay/{stayId}")
    public List<Booking> getBookingsByStayId(@PathVariable Long stayId) {
        return bookingRepository.findByStayId(stayId);
    }

    @PatchMapping("/accept/{id}")
    public Booking acceptBooking(@PathVariable Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id " + id));
        booking.setStatus(BookingStatus.ACCEPTED);
        return bookingRepository.save(booking);
    }

    @PatchMapping("/reject/{id}")
    public Booking rejectBooking(@PathVariable Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id " + id));
        booking.setStatus(BookingStatus.REJECTED);
        return bookingRepository.save(booking);
    }

}
