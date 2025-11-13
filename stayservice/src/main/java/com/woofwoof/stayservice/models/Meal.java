package com.woofwoof.stayservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;

@Data
@Entity
public class Meal {
    @Id
    @GeneratedValue
    private Long id_meal;

    @Column(nullable = false)
    private String label;

    @ManyToOne
    @JoinColumn(name = "id_stay")
    private Stay stay;
}
