package com.woofwoof.stayservice;

import com.woofwoof.stayservice.entities.Review;
import com.woofwoof.stayservice.entities.Stay;
import com.woofwoof.stayservice.entities.subclass.*;
import com.woofwoof.stayservice.repositories.StayRepository;
import com.woofwoof.stayservice.repositories.subclass.*;
import com.woofwoof.stayservice.services.GeocodingService;
import com.woofwoof.stayservice.services.StayService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StayServiceTest {

    private StayRepository stayRepository;
    private GeocodingService geocodingService;
    private ActivityRepository activityRepo;
    private MealRepository mealRepo;
    private LearningSkillRepository skillRepo;
    private AccomodationRepository accomodationRepo;

    private StayService stayService;

    @BeforeEach
    void setup() {
        stayRepository = mock(StayRepository.class);
        geocodingService = mock(GeocodingService.class);
        activityRepo = mock(ActivityRepository.class);
        mealRepo = mock(MealRepository.class);
        skillRepo = mock(LearningSkillRepository.class);
        accomodationRepo = mock(AccomodationRepository.class);

        stayService = new StayService(
                stayRepository,
                geocodingService,
                activityRepo,
                mealRepo,
                skillRepo,
                accomodationRepo
        );
    }

    @Test
    void createStay_setsDepartmentAndRegion_whenLocalisationProvided() {
        Stay stay = new Stay();
        stay.setTitle("Test stay");
        stay.setLocalisation(new Long[]{48L, 2L});
        stay.setWooferId(UUID.randomUUID());

        stay.setActivities(List.of());
        stay.setMeals(List.of());
        stay.setLearningSkills(List.of());
        stay.setAccomodations(List.of());

        var info = new GeocodingService.LocationInfo("Occitanie", "Hérault");
        when(geocodingService.getLocationInfo(48, 2)).thenReturn(info);
        when(stayRepository.existsByWooferId(any())).thenReturn(false);
        when(stayRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Stay result = stayService.createStay(stay);

        assertEquals("Occitanie", result.getDepartment());
        assertEquals("Hérault", result.getRegion());
    }

    @Test
    void createStay_attachesActivitiesMealsSkillsAccomodations() {
        Stay stay = new Stay();
        stay.setTitle("Complete stay");
        stay.setLocalisation(null);
        stay.setWooferId(UUID.randomUUID());

        Activity a = new Activity();
        a.setId(1L);
        Meal m = new Meal();
        m.setId(2L);
        LearningSkill s = new LearningSkill();
        s.setId(3L);
        Accomodation ac = new Accomodation();
        ac.setId(4L);

        stay.setActivities(new ArrayList<>(List.of(a)));
        stay.setMeals(new ArrayList<>(List.of(m)));
        stay.setLearningSkills(new ArrayList<>(List.of(s)));
        stay.setAccomodations(new ArrayList<>(List.of(ac)));

        when(stayRepository.existsByWooferId(any())).thenReturn(false);

        when(activityRepo.findById(1L)).thenReturn(Optional.of(new Activity()));
        when(mealRepo.findById(2L)).thenReturn(Optional.of(new Meal()));
        when(skillRepo.findById(3L)).thenReturn(Optional.of(new LearningSkill()));
        when(accomodationRepo.findById(4L)).thenReturn(Optional.of(new Accomodation()));

        when(stayRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Stay result = stayService.createStay(stay);

        assertEquals(1, result.getActivities().size());
        assertEquals(1, result.getMeals().size());
        assertEquals(1, result.getLearningSkills().size());
        assertEquals(1, result.getAccomodations().size());

        assertSame(result, result.getActivities().get(0).getStay());
        assertSame(result, result.getMeals().get(0).getStay());
    }

    @Test
    void getStayById_returnsStay() {
        Stay stay = new Stay();
        stay.setId(10L);

        when(stayRepository.findById(10L)).thenReturn(Optional.of(stay));

        Stay result = stayService.getStayById(10L);

        assertEquals(10L, result.getId());
    }

    @Test
    void getStayById_throws_whenNotFound() {
        when(stayRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> stayService.getStayById(99L));
    }

    @Test
    void deleteStay_callsRepository() {
        doNothing().when(stayRepository).deleteById(1L);
        stayService.deleteStay(1L);
        verify(stayRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateStay_savesWhenExists() {
        Stay stay = new Stay();
        stay.setId(5L);

        when(stayRepository.existsById(5L)).thenReturn(true);
        when(stayRepository.save(stay)).thenReturn(stay);

        Stay result = stayService.updateStay(stay);

        assertEquals(stay, result);
    }

    @Test
    void updateStay_throwsWhenNotExists() {
        Stay stay = new Stay();
        stay.setId(123L);

        when(stayRepository.existsById(123L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> stayService.updateStay(stay));
    }

    @Test
    void getMealsById_returnsMeals() {
        Stay stay = new Stay();
        Meal m = new Meal();
        stay.setMeals(List.of(m));

        when(stayRepository.findById(1L)).thenReturn(Optional.of(stay));

        List<Meal> result = stayService.getMealsById(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getActivitiesById_returnsActivities() {
        Stay s = new Stay();
        s.setActivities(List.of(new Activity()));

        when(stayRepository.findById(1L)).thenReturn(Optional.of(s));

        assertEquals(1, stayService.getActivitiesById(1L).size());
    }

    @Test
    void getLearningSkillsById_returnsSkills() {
        Stay s = new Stay();
        s.setLearningSkills(List.of(new LearningSkill()));

        when(stayRepository.findById(1L)).thenReturn(Optional.of(s));

        assertEquals(1, stayService.getLearningSkillsById(1L).size());
    }

    @Test
    void getAccommodationsById_returnsAccomodations() {
        Stay s = new Stay();
        s.setAccomodations(List.of(new Accomodation()));

        when(stayRepository.findById(1L)).thenReturn(Optional.of(s));

        assertEquals(1, stayService.getAccommodationsById(1L).size());
    }

    @Test
    void getReviewsById_returnsReviews() {
        Stay s = new Stay();
        s.setReviews(List.of(new Review()));

        when(stayRepository.findById(1L)).thenReturn(Optional.of(s));

        assertEquals(1, stayService.getReviewsById(1L).size());
    }

}
