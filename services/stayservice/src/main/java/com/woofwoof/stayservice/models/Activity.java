package com.woofwoof.stayservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Activity {
    @Id
    @GeneratedValue
    private Long id_activity;

    private String label;

    private String description;
}
