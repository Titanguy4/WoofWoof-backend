package com.woofwoof.stayservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Accomodation {
    @GeneratedValue
    private Long id_accomodation;

    public String label;

    @ManyToOne
    private Stay stay;
}
