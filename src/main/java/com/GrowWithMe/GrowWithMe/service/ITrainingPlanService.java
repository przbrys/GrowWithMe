package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Exercise;
import com.GrowWithMe.GrowWithMe.model.TrainingPlan;

import java.util.List;
import java.util.Optional;

public interface ITrainingPlanService {
    List<TrainingPlan> getAllTrainingPlan();
//    List<TrainingPlan> getAllClientTrainingPlan(Client client);
    Optional<TrainingPlan> getTrainingPlanById(Integer trainingPlanId);
    void deleteTrainingPlanEntity(Integer trainingPlanId);
    TrainingPlan createTrainingPlanEntity(TrainingPlan trainingPlan, List<Exercise> exerciseList);
    TrainingPlan updateTrainingPlan(TrainingPlan trainingPlanToUpdate);
}
