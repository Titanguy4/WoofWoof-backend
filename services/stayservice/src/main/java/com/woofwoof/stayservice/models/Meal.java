package com.woofwoof.stayservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Meal {
    @Id
    @GeneratedValue
    private Long id_meal;

    private String label;

    @ManyToOne
    private Stay stay;
}
