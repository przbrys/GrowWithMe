package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.TrainingPlan;

import java.util.List;
import java.util.Optional;

public interface ITrainingPlanService {
    List<TrainingPlan> getAllTrainingPlan();

    List<TrainingPlan> getTrainingPlansByTrainerId(Integer trainerId);

    Optional<TrainingPlan> getTrainingPlanById(Integer trainingPlanId);

    void deleteTrainingPlanEntity(Integer trainingPlanId);

    TrainingPlan createTrainingPlanEntity(TrainingPlan trainingPlan);

    TrainingPlan updateTrainingPlan(TrainingPlan trainingPlanToUpdate);
}
