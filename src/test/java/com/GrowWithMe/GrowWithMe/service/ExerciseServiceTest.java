package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.Exercise;
import com.GrowWithMe.GrowWithMe.repository.IExerciseRepository;
import com.GrowWithMe.GrowWithMe.service.impl.ExerciseService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {

    private static final int EXERCISE_ID = 1;
    private static final Exercise EXERCISE = generateExercise();

    @Mock
    private IExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseService tested;

    @Test
    void getAllExercise() {
        //given
        List<Exercise> exercises = List.of(EXERCISE);

        //when
        when(exerciseRepository.findAll()).thenReturn(exercises);
        List<Exercise> result = tested.getAllExercise();

        //then
        assertThat(result).isEqualTo(exercises);
    }

    @Test
    void getExerciseById__1() {
        //given //when
        when(exerciseRepository.findById(EXERCISE_ID)).thenReturn(Optional.of(EXERCISE));
        Optional<Exercise> result = tested.getExerciseById(EXERCISE_ID);

        //then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(EXERCISE);
    }

    @Test
    void getExerciseById__2() {
        //given //when
        when(exerciseRepository.findById(EXERCISE_ID)).thenReturn(Optional.empty());
        Optional<Exercise> result = tested.getExerciseById(EXERCISE_ID);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void deleteExerciseEntity__1() {
        //given //when
        tested.deleteExerciseEntity(EXERCISE_ID);

        //then
        verify(exerciseRepository).deleteById(EXERCISE_ID);
    }

    @Test
    void deleteExerciseEntity__2() {
        //given //when //then
        doThrow(EmptyResultDataAccessException.class).when(exerciseRepository).deleteById(EXERCISE_ID);

        assertThatThrownBy(() -> tested.deleteExerciseEntity(EXERCISE_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Exercise entity with id " + EXERCISE_ID + " not found");
    }

    @Test
    void deleteExerciseEntity__3() {
        //given //when //then
        doThrow(RuntimeException.class).when(exerciseRepository).deleteById(EXERCISE_ID);

        assertThatThrownBy(() -> tested.deleteExerciseEntity(EXERCISE_ID))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deleting Exercise");
    }

    @Test
    void createExerciseEntity__1() {
        //given //when
        Exercise result = tested.createExerciseEntity(EXERCISE);

        //then
        verify(exerciseRepository).save(EXERCISE);
        assertThat(result).isEqualTo(EXERCISE);
    }

    @Test
    void createExerciseEntity__2() {
        //given //when //then
        doThrow(RuntimeException.class).when(exerciseRepository).save(EXERCISE);

        assertThatThrownBy(() -> tested.createExerciseEntity(EXERCISE))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error in creating new Exercise.");
    }

    @Test
    void updateExercise__1() {
        //given
        Exercise exerciseToUpdate = new Exercise();
        exerciseToUpdate.setExerciseId(EXERCISE_ID);
        exerciseToUpdate.setExerciseName("FrontSquats");
        exerciseToUpdate.setExerciseNumberOfSeries(4);
        exerciseToUpdate.setExerciseNumberOfRepetitions(12);
        exerciseToUpdate.setExerciseInformations("Tech 2: ...");

        Exercise expectedExercise = generateExercise();
        expectedExercise.setExerciseId(EXERCISE_ID);
        expectedExercise.setExerciseName("FrontSquats");
        expectedExercise.setExerciseNumberOfSeries(4);
        expectedExercise.setExerciseNumberOfRepetitions(12);
        expectedExercise.setExerciseInformations("Tech 2: ...");

        //when
        when(exerciseRepository.findById(EXERCISE_ID)).thenReturn(Optional.of(EXERCISE));
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(expectedExercise);

        Exercise result = tested.updateExercise(exerciseToUpdate);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getExerciseName()).isEqualTo("FrontSquats");
        assertThat(result.getExerciseNumberOfSeries()).isEqualTo(4);
        assertThat(result.getExerciseNumberOfRepetitions()).isEqualTo(12);
        assertThat(result.getExerciseInformations()).isEqualTo("Tech 2: ...");
        verify(exerciseRepository).save(any(Exercise.class));
    }

    @Test
    void updateExercise__2() {
        //given
        Exercise exerciseToUpdate = new Exercise();
        exerciseToUpdate.setExerciseId(EXERCISE_ID);

        //when
        when(exerciseRepository.findById(EXERCISE_ID)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> tested.updateExercise(exerciseToUpdate))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Exercise entity with id " + EXERCISE_ID + " not found, update failed");
    }

    private static Exercise generateExercise() {
        Exercise exercise = new Exercise();
        exercise.setExerciseId(EXERCISE_ID);
        exercise.setExerciseName("Squats");
        exercise.setExerciseNumberOfSeries(3);
        exercise.setExerciseNumberOfRepetitions(10);
        exercise.setExerciseInformations("Tech: ... ");
        return exercise;
    }
}