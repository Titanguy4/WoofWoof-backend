package com.woofwoof.stayservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stay {
    @Id
    @GeneratedValue
    private Long id_stay;

    private String title;

    private String description;

    private Long[] localisation;

    private Date startDate;

    private Date endDate;

    private Boolean status;
    
    @OneToMany(mappedBy = "stay", cascade =  CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "stay", cascade =  CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<LearningSkill> learningSkills = new ArrayList<>();

    @OneToMany(mappedBy = "stay", cascade =  CascadeType.ALL, orphanRemoval = true)    
    @Builder.Default
    private List<Meal> meals = new ArrayList<>();

    @OneToMany(mappedBy = "stay", cascade =  CascadeType.ALL, orphanRemoval = true)    
    @Builder.Default
    private List<Accomodation> accommodations = new ArrayList<>();
    
    @ElementCollection
    private List<String> photos;

    public void addAccommodation(Accomodation accommodation) {
        accommodations.add(accommodation);
        accommodation.setStay(this);
    }
    
    public void removeAccommodation(Accomodation accommodation) {
        accommodations.remove(accommodation);
        accommodation.setStay(null);
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
}
