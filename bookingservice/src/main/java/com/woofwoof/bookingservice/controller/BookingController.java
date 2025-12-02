package com.woofwoof.bookingservice.controller;

import java.util.List;
import java.util.UUID;

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
import com.woofwoof.bookingservice.entity.BookingStatus;
import com.woofwoof.bookingservice.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /** GET all bookings */
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    /** GET booking by id */
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    /** POST create booking */
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody @Validated BookingCreateDTO booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    /** GET bookings by stayId */
    @GetMapping("/stay/{stayId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByStayId(@PathVariable Long stayId) {
        return ResponseEntity.ok(bookingService.getBookingsByStayId(stayId));
    }

    /** GET bookings by userId */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
    }

    /** PATCH accept booking */
    @PatchMapping("/accept/{id}")
    public ResponseEntity<BookingDTO> acceptBooking(@PathVariable Long id) {
        bookingService.updateBookingStatus(id, BookingStatus.ACCEPTED);
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    /** PATCH reject booking */
    @PatchMapping("/reject/{id}")
    public ResponseEntity<BookingDTO> rejectBooking(@PathVariable Long id) {
        bookingService.updateBookingStatus(id, BookingStatus.REJECTED);
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }
}
