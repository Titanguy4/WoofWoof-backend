package com.woofwoof.stayservice.models.subclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woofwoof.stayservice.models.Stay;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Builder;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {
    @Id @GeneratedValue
    @Column(name = "id_activity")
    private Long id;

    @Column(nullable = false)
    private String label;

    @ManyToOne
    @JoinColumn(name = "id_stay")
    @JsonIgnore
    private Stay stay;
}
