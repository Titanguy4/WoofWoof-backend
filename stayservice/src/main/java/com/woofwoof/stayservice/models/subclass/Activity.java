package com.woofwoof.stayservice.models.subclass;

import com.woofwoof.stayservice.models.Stay;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Builder;

@Data
@Entity
@Builder
public class Activity {
    @Id
    @GeneratedValue
    @Column(name = "id_activity")
    private Long id;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false, length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_stay")
    private Stay stay;
}
