package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.Exercise;
import java.util.List;
import java.util.Optional;

public interface IExerciseService {
    List<Exercise> getAllExercise();
    Optional<Exercise> getExerciseById(Integer exerciseId);
    void deleteExerciseEntity(Integer exerciseId);
    Exercise createExerciseEntity(Exercise exercise);
    Exercise updateExercise(Exercise exerciseToUpdate);
}
