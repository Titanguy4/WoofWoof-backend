package com.woofwoof.stayservice.entities;

import java.util.ArrayList;
import java.util.List;

import com.woofwoof.stayservice.entities.subclass.Accomodation;
import com.woofwoof.stayservice.entities.subclass.Activity;
import com.woofwoof.stayservice.entities.subclass.LearningSkill;
import com.woofwoof.stayservice.entities.subclass.Meal;
import jakarta.persistence.EnumType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stay {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StayType type;

    @Column(nullable = false)
    private Double[] localisation;

    @Column
    private String department;

    @Column
    private String region;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private Long wooferId;

    private Long bookingId;

    @OneToMany(mappedBy = "stay", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "stay", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<LearningSkill> learningSkills = new ArrayList<>();

    @OneToMany(mappedBy = "stay", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Meal> meals = new ArrayList<>();

    @OneToMany(mappedBy = "stay", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Accomodation> accomodations = new ArrayList<>();

    @OneToMany(mappedBy = "stay", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    /*
     * @OneToMany(mappedBy = "stay", cascade = CascadeType.ALL, orphanRemoval =
     * true)
     * 
     * @Column(nullable = false)
     * private List<Long> photoId;
     */

    public void addAccommodation(Accomodation accomodation) {
        accomodations.add(accomodation);
        accomodation.setStay(this);
    }

    public void removeAccommodation(Accomodation accomodation) {
        accomodations.remove(accomodation);
        accomodation.setStay(null);
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
        activity.setStay(this);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
        activity.setStay(null);
    }

    public void addLearningSkill(LearningSkill learningSkill) {
        learningSkills.add(learningSkill);
        learningSkill.setStay(this);
    }

    public void removeLearningSkill(LearningSkill learningSkill) {
        learningSkills.remove(learningSkill);
        learningSkill.setStay(null);
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
        meal.setStay(this);
    }

    public void removeMeal(Meal meal) {
        meals.remove(meal);
        meal.setStay(null);
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setStay(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setStay(null);
    }

    /*
     * /
     * public void addPhotoId(Long photoId) {
     * this.photoId.add(photoId);
     * }
     * 
     * public void removePhotoId(Long photoId) {
     * this.photoId.remove(photoId);
     * }
     */
}
