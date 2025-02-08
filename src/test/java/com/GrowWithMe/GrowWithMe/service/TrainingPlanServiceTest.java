package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Exercise;
import com.GrowWithMe.GrowWithMe.model.TrainingPlan;
import com.GrowWithMe.GrowWithMe.repository.ITrainingPlanRepository;
import com.GrowWithMe.GrowWithMe.service.impl.TrainingPlanService;
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
public class TrainingPlanServiceTest {

    private static final int TRAINING_PLAN_ID = 1;
    private static final int TRAINER_ID = 2;
    private static final Client CLIENT = new Client();
    private static final Exercise EXERCISE = new Exercise();
    private static final TrainingPlan TRAINING_PLAN = generateTrainingPlan();

    @Mock
    private ITrainingPlanRepository trainingPlanRepository;

    @InjectMocks
    private TrainingPlanService tested;

    @Test
    void getAllTrainingPlan() {
        //given
        List<TrainingPlan> trainingPlans = List.of(TRAINING_PLAN);

        //when
        when(trainingPlanRepository.findAll()).thenReturn(trainingPlans);
        List<TrainingPlan> result = tested.getAllTrainingPlan();

        //then
        assertThat(result).isEqualTo(trainingPlans);
    }

    @Test
    void getTrainingPlansByTrainerId() {
        //given
        List<TrainingPlan> trainingPlans = List.of(TRAINING_PLAN);

        //when
        when(trainingPlanRepository.getTrainingPlansByTrainerId(TRAINER_ID)).thenReturn(trainingPlans);
        List<TrainingPlan> result = tested.getTrainingPlansByTrainerId(TRAINER_ID);

        //then
        assertThat(result).isEqualTo(trainingPlans);
    }

    @Test
    void getTrainingPlanById__1() {
        //given //when
        when(trainingPlanRepository.findById(TRAINING_PLAN_ID)).thenReturn(Optional.of(TRAINING_PLAN));
        Optional<TrainingPlan> result = tested.getTrainingPlanById(TRAINING_PLAN_ID);

        //then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(TRAINING_PLAN);
    }

    @Test
    void getTrainingPlanById__2() {
        //given //when
        when(trainingPlanRepository.findById(TRAINING_PLAN_ID)).thenReturn(Optional.empty());
        Optional<TrainingPlan> result = tested.getTrainingPlanById(TRAINING_PLAN_ID);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void deleteTrainingPlanEntity__1() {
        //given //when //then
        tested.deleteTrainingPlanEntity(TRAINING_PLAN_ID);

        verify(trainingPlanRepository).deleteById(TRAINING_PLAN_ID);
    }

    @Test
    void deleteTrainingPlanEntity__2() {
        //given //when //then
        doThrow(EmptyResultDataAccessException.class).when(trainingPlanRepository).deleteById(TRAINING_PLAN_ID);

        assertThatThrownBy(() -> tested.deleteTrainingPlanEntity(TRAINING_PLAN_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("TrainingPlan entity with id " + TRAINING_PLAN_ID + " not found");
    }

    @Test
    void deleteTrainingPlanEntity__3() {
        //given //when //then
        doThrow(RuntimeException.class).when(trainingPlanRepository).deleteById(TRAINING_PLAN_ID);

        assertThatThrownBy(() -> tested.deleteTrainingPlanEntity(TRAINING_PLAN_ID))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deleting TrainingPlan");
    }

    @Test
    void createTrainingPlanEntity__1() {
        //given //when //then
        tested.createTrainingPlanEntity(TRAINING_PLAN);

        verify(trainingPlanRepository).save(TRAINING_PLAN);
    }

    @Test
    void createTrainingPlanEntity__2() {
        //given //when //then
        doThrow(RuntimeException.class).when(trainingPlanRepository).save(TRAINING_PLAN);

        assertThatThrownBy(() -> tested.createTrainingPlanEntity(TRAINING_PLAN))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error in creating new TrainingPlan.");
    }

    @Test
    void updateTrainingPlan__1() {
        //given
        TrainingPlan trainingPlanToUpdate = new TrainingPlan();
        trainingPlanToUpdate.setTrainingPlanId(TRAINING_PLAN_ID);
        trainingPlanToUpdate.setTrainingPlanName("Updated Training Plan");
        trainingPlanToUpdate.setClient(CLIENT);
        trainingPlanToUpdate.setExerciseList(emptyList());

        TrainingPlan expectedTrainingPlan = generateTrainingPlan();
        expectedTrainingPlan.setTrainingPlanId(TRAINING_PLAN_ID);
        expectedTrainingPlan.setTrainingPlanName("Updated Training Plan");
        expectedTrainingPlan.setClient(CLIENT);
        expectedTrainingPlan.setExerciseList(emptyList());

        //when
        when(trainingPlanRepository.findById(TRAINING_PLAN_ID)).thenReturn(Optional.of(TRAINING_PLAN));
        when(trainingPlanRepository.save(any(TrainingPlan.class))).thenReturn(expectedTrainingPlan);

        TrainingPlan result = tested.updateTrainingPlan(trainingPlanToUpdate);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getTrainingPlanName()).isEqualTo("Updated Training Plan");
        assertThat(result.getClient()).isEqualTo(CLIENT);
        assertThat(result.getExerciseList()).isEqualTo(emptyList());
        verify(trainingPlanRepository).save(any(TrainingPlan.class));
    }

    @Test
    void updateTrainingPlan__2() {
        //given
        TrainingPlan trainingPlanToUpdate = new TrainingPlan();
        trainingPlanToUpdate.setTrainingPlanId(TRAINING_PLAN_ID);

        //when
        when(trainingPlanRepository.findById(TRAINING_PLAN_ID)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> tested.updateTrainingPlan(trainingPlanToUpdate))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("TrainingPlan entity with id " + TRAINING_PLAN_ID + " not found, update failed");
    }

    private static TrainingPlan generateTrainingPlan() {
        TrainingPlan trainingPlan = new TrainingPlan();
        trainingPlan.setTrainingPlanId(TRAINING_PLAN_ID);
        trainingPlan.setTrainingPlanName("Test Training Plan");
        trainingPlan.setClient(CLIENT);
        trainingPlan.setExerciseList(List.of(EXERCISE));
        return trainingPlan;
    }
}