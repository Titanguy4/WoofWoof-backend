package com.woofwoof.stayservice.models.subclass;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woofwoof.stayservice.models.Stay;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;

@Data
@Entity
public class Accomodation {
    @GeneratedValue
    @Id
    @Column(name = "id_accomodation")
    private Long id;

    @Column(nullable = false)
    public String label;

    @ManyToOne
    @JoinColumn(name = "id_stay")
    @JsonIgnore
    private Stay stay;
}
