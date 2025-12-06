package com.woofwoof.stayservice.entities;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @GeneratedValue
    @Id
    @Column(name = "id_review")
    private Long id;

    private String name;

    private String country;

    private Long rating;

    private Date date;

    private String content;

    @ManyToOne
    @JoinColumn(name = "id_stay")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIgnore
    private Stay stay;

}
