package com.woofwoof.woofplanner.dto;

public record StayDTO(
                Long id,
                String title,
                Long[] localisation,
                String description) {
}
