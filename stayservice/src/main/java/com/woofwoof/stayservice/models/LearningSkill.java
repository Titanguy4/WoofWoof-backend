package com.woofwoof.stayservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class LearningSkill {
    @Id
    @GeneratedValue
    private String id_skill;

    private String label;

    @ManyToOne
    private Stay stay;
}
