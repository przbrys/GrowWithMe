package com.GrowWithMe.GrowWithMe.controller;

import com.GrowWithMe.GrowWithMe.model.DietPlan;
import com.GrowWithMe.GrowWithMe.model.Meal;
import com.GrowWithMe.GrowWithMe.service.impl.DietPlanService;
import com.GrowWithMe.GrowWithMe.service.impl.MealService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/meal")
public class MealController {
    @Autowired
    private MealService mealService;
    @Autowired
    private DietPlanService dietPlanService;

    @GetMapping
    public ResponseEntity<List<Meal>> getAllMeal() {
        List<Meal> mealList = mealService.getAllMeal();
        return new ResponseEntity<>(mealList, mealList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable Integer id) {
        Optional<Meal> mealOptional = mealService.getMealById(id);
        return mealOptional.map(meal -> new ResponseEntity<>(meal, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Meal> createNewMealEntity(@RequestBody Meal meal) {
        Meal createdMeal = mealService.createMealEntity(meal);
        return new ResponseEntity<>(createdMeal, HttpStatus.CREATED);
    }

    @PostMapping("/{dietPlanId}")
    public ResponseEntity<Meal> createNewMealEntity(@RequestBody Meal meal, @PathVariable Integer dietPlanId) {
        Meal createdMeal = mealService.createMealEntity(meal);
        Optional<DietPlan> dietPlanOptional = dietPlanService.getDietPlanById(dietPlanId);
        if (dietPlanOptional.isPresent()) {
            DietPlan dietPlan = dietPlanOptional.get();
            dietPlan.getMealList().add(createdMeal);
            dietPlan.setMealList(dietPlan.getMealList());
            dietPlanService.updateDietPlan(dietPlan);

            return new ResponseEntity<>(createdMeal, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealById(@PathVariable Integer id) {
        try {
            mealService.deleteMealEntity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping
    public ResponseEntity<Meal> updateMeal(@RequestBody Meal mealToUpdate) {
        try {
            Meal updatedMeal = mealService.updateMealInformation(mealToUpdate);
            return new ResponseEntity<>(updatedMeal, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
