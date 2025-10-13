package com.woofwoof.stayservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Data;

@Data
@Entity
public class Stayservice {
    @GeneratedValue
    private Long Id;
}
