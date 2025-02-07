package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.DietPlan;

import java.util.List;
import java.util.Optional;

public interface IDietPlanService {
    List<DietPlan> getAllDietPlan();

    List<DietPlan> getDietPlansByTrainerId(Integer trainerId);

    Optional<DietPlan> getDietPlanById(Integer dietPlanId);

    void deleteDietPlanEntity(Integer dietPlanId);

    DietPlan createDietPlanEntity(DietPlan dietPlan);

    DietPlan updateDietPlan(DietPlan dietPlanToUpdate);
}
