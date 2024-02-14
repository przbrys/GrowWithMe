package com.GrowWithMe.GrowWithMe.service.impl;
import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Exercise;
import com.GrowWithMe.GrowWithMe.model.TrainingPlan;
import com.GrowWithMe.GrowWithMe.repository.ITrainingPlanRepository;
import com.GrowWithMe.GrowWithMe.service.ITrainingPlanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TrainingPlanService implements ITrainingPlanService {
    @Autowired
    private ITrainingPlanRepository trainingPlanRepository;

    @Override
    public List<TrainingPlan> getAllTrainingPlan() {
        return trainingPlanRepository.findAll();
    }

//    @Override
//    public List<TrainingPlan> getAllClientTrainingPlan(Client client) {
//        return trainingPlanRepository.findByClient(client);
//    }

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
    public TrainingPlan createTrainingPlanEntity(TrainingPlan trainingPlan, List<Exercise> exerciseList) {
        try {
            trainingPlan.setExerciseList(exerciseList);
            trainingPlanRepository.save(trainingPlan);
            return trainingPlan;
        } catch (Exception e) {
            throw new RuntimeException("Error in creating new TrainingPlan.", e);
        }
    }

    @Override
    public TrainingPlan updateTrainingPlan(TrainingPlan trainingPlanToUpdate) {
        Optional<TrainingPlan> trainingPlanOptional = trainingPlanRepository.findById(trainingPlanToUpdate.getTrainingPlanId());
        if (trainingPlanOptional.isPresent()) {
            return trainingPlanRepository.save(trainingPlanToUpdate);
        } else {
            throw new EntityNotFoundException("TrainingPlan entity with id " + trainingPlanToUpdate.getTrainingPlanId() + " not found, update failed");
        }
    }
}