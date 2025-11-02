package com.woofwoof.bookingservice.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long missionId;
    private Long userId;
    private LocalDate startRequestedDate;
    private LocalDate endRequestedDate;
    private String status;
    private String email;
    private String number;

    // --- Getters et Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMissionId() { return missionId; }
    public void setMissionId(Long missionId) { this.missionId = missionId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDate getStartRequestedDate() { return startRequestedDate; }
    public void setRequestedDate(LocalDate startRequestedDate) { this.startRequestedDate = startRequestedDate; }

    public LocalDate getEndRequestedDate() { return endRequestedDate; }
    public void setEndRequestedDate(LocalDate endRequestedDate) { this.endRequestedDate = endRequestedDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
}
