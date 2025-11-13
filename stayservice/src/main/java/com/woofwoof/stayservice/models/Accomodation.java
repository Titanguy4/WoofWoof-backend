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
public class Accomodation {
    @GeneratedValue
    @Id
    private Long id_accomodation;

    @Column(nullable = false)
    public String label;

    @ManyToOne
    @JoinColumn(name = "id_stay")
    private Stay stay;
}
