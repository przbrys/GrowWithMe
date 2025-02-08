package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.DietPlan;
import com.GrowWithMe.GrowWithMe.model.Meal;
import com.GrowWithMe.GrowWithMe.repository.IDietPlanRepository;
import com.GrowWithMe.GrowWithMe.service.impl.DietPlanService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DietPlanServiceTest {

    private static final int DIET_PLAN_ID = 1;
    private static final int TRAINER_ID = 2;
    private static final Client CLIENT = new Client();
    private static final Meal MEAL = new Meal();
    private static final DietPlan DIET_PLAN = generateDietPlan();

    @Mock
    private IDietPlanRepository dietPlanRepository;

    @InjectMocks
    private DietPlanService tested;

    @Test
    void getAllDietPlan() {
        //given
        List<DietPlan> dietPlans = List.of(DIET_PLAN);

        //when
        when(dietPlanRepository.findAll()).thenReturn(dietPlans);
        List<DietPlan> result = tested.getAllDietPlan();

        //then
        assertThat(result).isEqualTo(dietPlans);
    }

    @Test
    void getDietPlansByTrainerId() {
        //given
        List<DietPlan> dietPlans = List.of(DIET_PLAN);

        //when
        when(dietPlanRepository.getDietPlansByTrainerId(TRAINER_ID)).thenReturn(dietPlans);
        List<DietPlan> result = tested.getDietPlansByTrainerId(TRAINER_ID);

        //then
        assertThat(result).isEqualTo(dietPlans);
    }

    @Test
    void getDietPlanById__1() {
        //given //when
        when(dietPlanRepository.findById(DIET_PLAN_ID)).thenReturn(Optional.of(DIET_PLAN));
        Optional<DietPlan> result = tested.getDietPlanById(DIET_PLAN_ID);

        //then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(DIET_PLAN);
    }

    @Test
    void getDietPlanById__2() {
        //given //when
        when(dietPlanRepository.findById(DIET_PLAN_ID)).thenReturn(Optional.empty());
        Optional<DietPlan> result = tested.getDietPlanById(DIET_PLAN_ID);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void deleteDietPlanEntity__1() {
        //given //when
        tested.deleteDietPlanEntity(DIET_PLAN_ID);

        //then
        verify(dietPlanRepository).deleteById(DIET_PLAN_ID);
    }

    @Test
    void deleteDietPlanEntity__2() {
        //given //when //then
        doThrow(EmptyResultDataAccessException.class).when(dietPlanRepository).deleteById(DIET_PLAN_ID);

        assertThatThrownBy(() -> tested.deleteDietPlanEntity(DIET_PLAN_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("DietPlan entity with id " + DIET_PLAN_ID + " not found");
    }

    @Test
    void deleteDietPlanEntity__3() {
        //given //when //then
        doThrow(RuntimeException.class).when(dietPlanRepository).deleteById(DIET_PLAN_ID);

        assertThatThrownBy(() -> tested.deleteDietPlanEntity(DIET_PLAN_ID))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deleting DietPlan");
    }

    @Test
    void createDietPlanEntity__1() {
        //given //when
        tested.createDietPlanEntity(DIET_PLAN);

        //then
        verify(dietPlanRepository).save(DIET_PLAN);
    }

    @Test
    void createDietPlanEntity__2() {
        //given //when //then
        doThrow(RuntimeException.class).when(dietPlanRepository).save(DIET_PLAN);

        assertThatThrownBy(() -> tested.createDietPlanEntity(DIET_PLAN))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error in creating new DietPlan.");
    }

    @Test
    void updateDietPlan__1() {
        //given
        DietPlan dietPlanToUpdate = new DietPlan();
        dietPlanToUpdate.setDietPlanId(DIET_PLAN_ID);
        dietPlanToUpdate.setDietPlanName("newName");
        dietPlanToUpdate.setClient(CLIENT);
        dietPlanToUpdate.setMealList(emptyList());

        DietPlan expectedDietPlan = generateDietPlan();
        expectedDietPlan.setDietPlanId(DIET_PLAN_ID);
        expectedDietPlan.setDietPlanName("newName");
        expectedDietPlan.setClient(CLIENT);
        expectedDietPlan.setMealList(emptyList());

        //when
        when(dietPlanRepository.findById(DIET_PLAN_ID)).thenReturn(Optional.of(DIET_PLAN));
        when(dietPlanRepository.save(any(DietPlan.class))).thenReturn(expectedDietPlan);

        DietPlan result = tested.updateDietPlan(dietPlanToUpdate);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getDietPlanName()).isEqualTo("newName");
        assertThat(result.getClient()).isEqualTo(CLIENT);
        assertThat(result.getMealList()).isEqualTo(emptyList());
        verify(dietPlanRepository).save(any(DietPlan.class));
    }

    @Test
    void updateDietPlan__2() {
        //given
        DietPlan dietPlanToUpdate = new DietPlan();
        dietPlanToUpdate.setDietPlanId(DIET_PLAN_ID);

        //when
        when(dietPlanRepository.findById(DIET_PLAN_ID)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> tested.updateDietPlan(dietPlanToUpdate))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("DietPlan entity with id " + DIET_PLAN_ID + " not found, update failed");
    }

    private static DietPlan generateDietPlan() {
        DietPlan dietPlan = new DietPlan();
        dietPlan.setDietPlanId(DIET_PLAN_ID);
        dietPlan.setDietPlanName("name");
        dietPlan.setClient(CLIENT);
        dietPlan.setMealList(List.of(MEAL));
        return dietPlan;
    }
}