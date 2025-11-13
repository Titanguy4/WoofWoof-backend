package com.woofwoof.stayservice.models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Review {
    @GeneratedValue
    @Id
    private Long id_review;

    private String rating;

    private Date date;

    private String content;

    @ManyToOne
    @JoinColumn(name = "id_stay")
    private Stay stay;

}
