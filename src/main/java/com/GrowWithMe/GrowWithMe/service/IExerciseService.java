package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.Exercise;
import com.GrowWithMe.GrowWithMe.repository.IExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface IExerciseService {
    List<Exercise> getAllExercise();
    Optional<Exercise> getExerciseById(Integer exerciseId);
    void deleteExercise(Integer exerciseId);
    Exercise createExerciseEntity(Exercise exercise);
    Exercise updateExercise(Exercise exerciseToUpdate);
}
