package com.woofwoof.stayservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import jakarta.persistence.Column;

@Data
@Entity
public class LearningSkill {
    @Id
    @GeneratedValue
    private Long id_skill;

    @Column(nullable = false)
    private String label;

    @ManyToOne
    private Stay stay;
}
