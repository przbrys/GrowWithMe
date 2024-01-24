package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.DietPlan;
import com.GrowWithMe.GrowWithMe.model.Meal;

import java.util.List;
import java.util.Optional;

public interface IDietPlanService {
    List<DietPlan> getAllDietPlan();
    List<DietPlan> getAllClientDietPlan(Client client);
    Optional<DietPlan> getDietPlanById(Integer dietPlanId);
    void deleteDietPlanEntity(Integer dietPlanId);
    DietPlan createDietPlanEntity(DietPlan dietPlan, List<Meal> mealList);
    DietPlan updateDietPlan(DietPlan dietPlanToUpdate);
}
