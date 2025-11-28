package com.woofwoof.bookingservice.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long stayId;
    private UUID userId;
    private LocalDate startRequestedDate;
    private LocalDate endRequestedDate;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private String email;
    private String number;

    // --- Getters et Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStayId() {
        return stayId;
    }

    public void setStayId(Long stayId) {
        this.stayId = stayId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDate getStartRequestedDate() {
        return startRequestedDate;
    }

    public void setStartRequestedDate(LocalDate startRequestedDate) {
        this.startRequestedDate = startRequestedDate;
    }

    public LocalDate getEndRequestedDate() {
        return endRequestedDate;
    }

    public void setEndRequestedDate(LocalDate endRequestedDate) {
        this.endRequestedDate = endRequestedDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
