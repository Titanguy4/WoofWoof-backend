package com.woofwoof.stayservice.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Stay {
    @Id
    @GeneratedValue
    private Long id_stay;

    private String title;

    private String description;

    private int[] localisation;
    //private Long[] localisation;

    private Date startDate;

    private Date endDate;

    private Boolean status;
    
    @ManyToMany
    private List<Activity> activities;

    @ManyToMany
    private List<LearningSkill> learningSkills;

    @ManyToMany
    private List<Meal> meals;

    @ManyToMany
    private List<Accomodation> accomodations;
    
    @ElementCollection
    private List<String> photos;
}
