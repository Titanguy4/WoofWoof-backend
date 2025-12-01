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
                    LocalDate.of(2026, 1, 10),
                    LocalDate.of(2026, 1, 15),
                    BookingStatus.ACCEPTED,
                    "test1@example.com",
                    "0612345678"));

            // Booking 2
            bookingRepository.save(new Booking(
                    null,
                    2L,
                    UUID.fromString("8d75d3af-1066-430c-9a1d-579c8004a563"),
                    LocalDate.of(2026, 2, 5),
                    LocalDate.of(2026, 2, 12),
                    BookingStatus.PENDING,
                    "test2@example.com",
                    "0698765432"));

            // Booking 3
            bookingRepository.save(new Booking(
                    null,
                    5L,
                    UUID.fromString("8d75d3af-1066-430c-9a1d-579c8004a563"),
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
                    LocalDate.of(2026, 3, 20),
                    LocalDate.of(2026, 3, 25),
                    BookingStatus.PENDING,
                    "test4@example.com",
                    "0642424242"));

            // Booking 5
            bookingRepository.save(new Booking(
                    null,
                    4L,
                    UUID.fromString("8d75d3af-1066-430c-9a1d-579c8004a563"),
                    LocalDate.of(2026, 4, 10),
                    LocalDate.of(2026, 4, 18),
                    BookingStatus.PENDING,
                    "contact@client.com",
                    "0600112233"));

            System.out.println("✅ Bookings data seeded successfully!");
        };
    }
}
