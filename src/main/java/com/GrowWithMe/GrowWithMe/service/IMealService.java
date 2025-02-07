package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.Meal;

import java.util.List;
import java.util.Optional;

public interface IMealService {
    List<Meal> getAllMeal();

    Optional<Meal> getMealById(Integer mealId);

    void deleteMealEntity(Integer mealId);

    Meal createMealEntity(Meal meal);

    Meal updateMealInformation(Meal mealToUpdate);
}
