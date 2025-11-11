package com.woofwoof.stayservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Builder;

@Data
@Entity
@Builder
public class Activity {
    @Id
    @GeneratedValue
    private Long id_activity;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false, length = 2000)
    private String description;

    @ManyToOne
    private Stay stay;
}
