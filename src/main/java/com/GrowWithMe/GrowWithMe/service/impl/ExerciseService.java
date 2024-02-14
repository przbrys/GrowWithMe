package com.GrowWithMe.GrowWithMe.service.impl;
import com.GrowWithMe.GrowWithMe.model.Exercise;
import com.GrowWithMe.GrowWithMe.repository.IExerciseRepository;
import com.GrowWithMe.GrowWithMe.service.IExerciseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ExerciseService implements IExerciseService {
    @Autowired
    private IExerciseRepository exerciseRepository;
    @Override
    public List<Exercise> getAllExercise() { return exerciseRepository.findAll(); }

    @Override
    public Optional<Exercise> getExerciseById(Integer exerciseId) {
        return exerciseRepository.findById(exerciseId);
    }

    @Override
    public void deleteExerciseEntity(Integer exerciseId) {
        try {
            exerciseRepository.deleteById(exerciseId);
        }catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Exercise entity with id " + exerciseId + " not found", e);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Exercise", e);
        }
    }

    @Override
    public Exercise createExerciseEntity(Exercise exercise) {
        try {
            exerciseRepository.save(exercise);
            return exercise;
        }catch (Exception e){
            throw new RuntimeException("Error in creating new Exercise.",e);
        }
    }
    @Override
//    ToDo!!! need to add validation, musn't add null
    public Exercise updateExercise(Exercise exerciseToUpdate) {
        Optional<Exercise> exerciseOptional =exerciseRepository.findById(exerciseToUpdate.getExerciseId());
        if (exerciseOptional.isPresent()) {
            return exerciseRepository.save(exerciseToUpdate);
        } else {
            throw new EntityNotFoundException("Exercise entity with id " + exerciseToUpdate.getExerciseId() + " not found, update failed");
        }
    }
}
