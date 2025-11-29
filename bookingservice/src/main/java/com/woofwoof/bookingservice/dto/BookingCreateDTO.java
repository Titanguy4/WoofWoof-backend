package com.woofwoof.bookingservice.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookingCreateDTO(
                @NotNull(message = "L'ID du logement est obligatoire") Long stayId,
                @NotNull(message = "L'ID de l'utilisateur est obligatoire") UUID userId,
                @NotNull(message = "La date de début est obligatoire") @FutureOrPresent(message = "La date de début ne peut pas être dans le passé") LocalDate startRequestedDate,
                @NotNull(message = "La date de fin est obligatoire") LocalDate endRequestedDate,
                String status,
                @NotBlank @Email String email,
                String number) {
}
