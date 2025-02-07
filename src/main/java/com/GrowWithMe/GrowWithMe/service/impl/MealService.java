package com.GrowWithMe.GrowWithMe.service.impl;

import com.GrowWithMe.GrowWithMe.model.Meal;
import com.GrowWithMe.GrowWithMe.repository.IMealRepository;
import com.GrowWithMe.GrowWithMe.service.IMealService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealService implements IMealService {

    @Autowired
    private IMealRepository mealRepository;

    @Override
    public List<Meal> getAllMeal() {
        return mealRepository.findAll();
    }

    @Override
    public Optional<Meal> getMealById(Integer mealId) {
        return mealRepository.findById(mealId);
    }

    @Override
    public void deleteMealEntity(Integer mealId) {
        try {
            mealRepository.deleteById(mealId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Meal entity with id " + mealId + " not found", e);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Meal", e);
        }
    }

    @Override
    public Meal createMealEntity(Meal meal) {
        try {
            mealRepository.save(meal);
            return meal;
        } catch (Exception e) {
            throw new RuntimeException("Error in creating new Meal.", e);
        }
    }

    @Override
    public Meal updateMealInformation(Meal mealToUpdate) {
        Optional<Meal> mealOptional = mealRepository.findById(mealToUpdate.getMealId());
        if (mealOptional.isPresent()) {
            Meal meal = mealOptional.get();

            if (mealToUpdate.getMealIngredients() != null && !mealToUpdate.getMealIngredients().equals(meal.getMealIngredients()))
                meal.setMealIngredients(mealToUpdate.getMealIngredients());
            if (mealToUpdate.getMealName() != null && !mealToUpdate.getMealName().equals(meal.getMealName()))
                meal.setMealName(mealToUpdate.getMealName());
            if (mealToUpdate.getMealCaloricValue() != null && !mealToUpdate.getMealCaloricValue().equals(meal.getMealCaloricValue()))
                meal.setMealCaloricValue(mealToUpdate.getMealCaloricValue());
            if (mealToUpdate.getMealMacroElements() != null && !mealToUpdate.getMealMacroElements().equals(meal.getMealMacroElements()))
                meal.setMealMacroElements(mealToUpdate.getMealMacroElements());
            if (mealToUpdate.getMealPreparationDescription() != null && !mealToUpdate.getMealPreparationDescription().equals(meal.getMealPreparationDescription()))
                meal.setMealPreparationDescription(mealToUpdate.getMealPreparationDescription());

            return mealRepository.save(meal);
        } else {
            throw new EntityNotFoundException("Meal entity with id " + mealToUpdate.getMealId() + " not found, update failed");
        }
    }
}
