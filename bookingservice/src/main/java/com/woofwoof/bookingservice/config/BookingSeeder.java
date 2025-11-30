package com.woofwoof.bookingservice.config;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.woofwoof.bookingservice.entity.Booking;
import com.woofwoof.bookingservice.entity.BookingStatus;
import com.woofwoof.bookingservice.repository.BookingRepository;

@Configuration
public class BookingSeeder {

    @Bean
    CommandLineRunner initDatabase(BookingRepository bookingRepository) {
        return args -> {
            // Vérifier si des données existent déjà
            if (bookingRepository.count() > 0) {
                return;
            }

            // Booking 1
            bookingRepository.save(new Booking(
                    null,
                    1L,
                    UUID.fromString("8d75d3af-1066-430c-9a1d-579c8004a563"),
                    LocalDate.of(2025, 1, 10),
                    LocalDate.of(2025, 1, 15),
                    BookingStatus.PENDING,
                    "test1@example.com",
                    "0612345678"));

            // Booking 2
            bookingRepository.save(new Booking(
                    null,
                    2L,
                    UUID.fromString("7b64c2be-0955-320b-8a2c-468b7003b452"),
                    LocalDate.of(2025, 2, 5),
                    LocalDate.of(2025, 2, 12),
                    BookingStatus.ACCEPTED,
                    "test2@example.com",
                    "0698765432"));

            // Booking 3
            bookingRepository.save(new Booking(
                    null,
                    1L,
                    UUID.fromString("9e86d4cf-1177-541d-9b3d-670c9005c674"),
                    LocalDate.of(2025, 3, 1),
                    LocalDate.of(2025, 3, 5),
                    BookingStatus.REJECTED,
                    "test3@example.com",
                    "0777888999"));

            // Booking 4
            bookingRepository.save(new Booking(
                    null,
                    3L,
                    UUID.fromString("8d75d3af-1066-430c-9a1d-579c8004a563"),
                    LocalDate.of(2025, 3, 20),
                    LocalDate.of(2025, 3, 25),
                    BookingStatus.PENDING,
                    "test4@example.com",
                    "0642424242"));

            // Booking 5
            bookingRepository.save(new Booking(
                    null,
                    4L,
                    UUID.fromString("af97e5e0-2288-652e-ac4e-781da106d785"),
                    LocalDate.of(2025, 4, 10),
                    LocalDate.of(2025, 4, 18),
                    BookingStatus.ACCEPTED,
                    "contact@client.com",
                    "0600112233"));

            System.out.println("✅ Bookings data seeded successfully!");
        };
    }
}
