package com.woofwoof.stayservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Accomodation {
    @GeneratedValue
    @Id
    private Long id_accomodation;

    public String label;

    @ManyToOne
    private Stay stay;
}
