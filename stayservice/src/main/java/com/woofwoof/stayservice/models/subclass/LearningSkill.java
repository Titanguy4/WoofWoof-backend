package com.woofwoof.stayservice.models.subclass;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import com.woofwoof.stayservice.models.Stay;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;

@Data
@Entity
public class LearningSkill {
    @Id
    @GeneratedValue
    @Column(name = "id_skill")
    private Long id;

    @Column(nullable = false)
    private String label;

    @ManyToOne
    @JoinColumn(name = "id_stay")
    private Stay stay;
}
