package com.GrowWithMe.GrowWithMe.service.impl;
import com.GrowWithMe.GrowWithMe.model.Exercise;
import com.GrowWithMe.GrowWithMe.model.TrainingPlan;
import com.GrowWithMe.GrowWithMe.repository.ITrainingPlanRepository;
import com.GrowWithMe.GrowWithMe.service.ITrainingPlanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class TrainingPlanService implements ITrainingPlanService {
    @Autowired
    private ITrainingPlanRepository trainingPlanRepository;

    @Override
    public List<TrainingPlan> getAllTrainingPlan() {
        return trainingPlanRepository.findAll();
    }

    @Override
    public List<TrainingPlan> getTrainingPlansByTrainerId(Integer trainerId) {
        return trainingPlanRepository.getTrainingPlansByTrainerId(trainerId);
    }

    @Override
    public Optional<TrainingPlan> getTrainingPlanById(Integer trainingPlanId) {
        return trainingPlanRepository.findById(trainingPlanId);
    }

    @Override
    public void deleteTrainingPlanEntity(Integer trainingPlanId) {
        try {
            trainingPlanRepository.deleteById(trainingPlanId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("TrainingPlan entity with id " + trainingPlanId + " not found", e);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting TrainingPlan", e);
        }
    }

    @Override
    public TrainingPlan createTrainingPlanEntity(TrainingPlan trainingPlan) {
        try {
            return trainingPlanRepository.save(trainingPlan);
        } catch (Exception e) {
            throw new RuntimeException("Error in creating new TrainingPlan.", e);
        }
    }

    @Override
    public TrainingPlan updateTrainingPlan(TrainingPlan trainingPlanToUpdate) {
        Optional<TrainingPlan> trainingPlanOptional = trainingPlanRepository.findById(trainingPlanToUpdate.getTrainingPlanId());
        if (trainingPlanOptional.isPresent()) {
            TrainingPlan trainingPlan= trainingPlanOptional.get();

            if (trainingPlanToUpdate.getTrainingPlanName()!=null && !trainingPlanToUpdate.getTrainingPlanName().equals(trainingPlan.getTrainingPlanName()))
                trainingPlan.setTrainingPlanName(trainingPlanToUpdate.getTrainingPlanName());
            if(trainingPlanToUpdate.getClient()!=null && !Objects.equals(trainingPlanToUpdate.getClient(), trainingPlan.getClient()))
                trainingPlan.setClient(trainingPlanToUpdate.getClient());
            if(trainingPlanToUpdate.getExerciseList()!= null && !trainingPlanToUpdate.getExerciseList().isEmpty() && !Objects.deepEquals(trainingPlanToUpdate.getExerciseList(), (trainingPlan.getExerciseList())))
                trainingPlan.setExerciseList(trainingPlanToUpdate.getExerciseList());

            return trainingPlanRepository.save(trainingPlan);
        } else {
            throw new EntityNotFoundException("TrainingPlan entity with id " + trainingPlanToUpdate.getTrainingPlanId() + " not found, update failed");
        }
    }
}