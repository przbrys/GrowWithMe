package com.GrowWithMe.GrowWithMe.controller;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.DTO.ExistingEntryToClientDTO;
import com.GrowWithMe.GrowWithMe.model.DTO.ExistingMealToDietPlanDTO;
import com.GrowWithMe.GrowWithMe.model.DietPlan;
import com.GrowWithMe.GrowWithMe.model.Meal;
import com.GrowWithMe.GrowWithMe.service.impl.ClientService;
import com.GrowWithMe.GrowWithMe.service.impl.DietPlanService;
import com.GrowWithMe.GrowWithMe.service.impl.MealService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dietPlan")
public class DietPlanController {
    @Autowired
    private DietPlanService dietPlanService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private MealService mealService;

    @GetMapping
    public ResponseEntity<List<DietPlan>> getAllDietPlan() {
        List<DietPlan> dietPlanList = dietPlanService.getAllDietPlan();
        return new ResponseEntity<>(dietPlanList, dietPlanList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<DietPlan> getDietPlanById(@PathVariable Integer id) {
        Optional<DietPlan> dietPlanOptional = dietPlanService.getDietPlanById(id);
        return dietPlanOptional.map(dietPlan -> new ResponseEntity<>(dietPlan, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/trainerPlans")
    public ResponseEntity<List<DietPlan>> getDietPlansByTrainerId(@PathVariable Integer id) {
        List<DietPlan> dietPlanList = dietPlanService.getDietPlansByTrainerId(id);
        return new ResponseEntity<>(dietPlanList, dietPlanList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DietPlan> createDietPlanEntity(@RequestBody DietPlan dietPlan) {
        try {
            DietPlan dietPlanToCreate = dietPlanService.createDietPlanEntity(dietPlan);
            return new ResponseEntity<>(dietPlanToCreate, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/dietPlanToNewUser")
    public ResponseEntity<DietPlan> createDietPlanToNewUser(@RequestBody ExistingEntryToClientDTO existingDietPlanToClientDTO) {
        try {
            Optional<DietPlan> dietPlanOptional = dietPlanService.getDietPlanById(existingDietPlanToClientDTO.getEntryId());
            Optional<Client> clientOptional = clientService.getClientById(existingDietPlanToClientDTO.getNewUserId());
            if (dietPlanOptional.isPresent() && clientOptional.isPresent()) {
                DietPlan dietPlan = new DietPlan();
                dietPlan.setClient(clientOptional.get());
                List<Meal> mealList = new ArrayList<>(dietPlanOptional.get().getMealList());
                dietPlan.setMealList(mealList);
                dietPlan.setDietPlanName(dietPlanOptional.get().getDietPlanName());
                dietPlanService.createDietPlanEntity(dietPlan);
                return new ResponseEntity<>(dietPlan, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDietPlanEntity(@PathVariable Integer id) {
        try {
            dietPlanService.deleteDietPlanEntity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping
    public ResponseEntity<DietPlan> updateDietPlan(@RequestBody DietPlan dietPlanToUpdate) {
        try {
            DietPlan updatedDietPlan = dietPlanService.updateDietPlan(dietPlanToUpdate);
            return new ResponseEntity<>(updatedDietPlan, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/mealToDietPlan")
    public ResponseEntity<DietPlan> addMealToDietPlan(@RequestBody ExistingMealToDietPlanDTO existingMealToDietPlanDTO) {
        try {
            Optional<Meal> mealOptional = mealService.getMealById(existingMealToDietPlanDTO.getMealId());
            Optional<DietPlan> dietPlanOptional = dietPlanService.getDietPlanById(existingMealToDietPlanDTO.getDietPlanId());
            if (mealOptional.isPresent() && dietPlanOptional.isPresent()) {
                DietPlan dietPlan = dietPlanOptional.get();
                var meals = dietPlan.getMealList();
                Meal meal = new Meal(mealOptional.get());
                meals.add(mealService.createMealEntity(meal));
                dietPlan.setMealList(meals);
                dietPlanService.updateDietPlan(dietPlan);
                return new ResponseEntity<>(dietPlan, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/deleteMealFromDietPlanList")
    public ResponseEntity<DietPlan> deleteMealFromDietPlanList(@RequestBody ExistingMealToDietPlanDTO existingMealToDietPlanDTO) {
        try {
            Optional<DietPlan> dietPlanOptional = dietPlanService.getDietPlanById(existingMealToDietPlanDTO.getDietPlanId());
            Optional<Meal> mealOptional = mealService.getMealById(existingMealToDietPlanDTO.getMealId());
            if (mealOptional.isPresent() && dietPlanOptional.isPresent()) {
                DietPlan dietPlan = dietPlanOptional.get();
                dietPlan.getMealList().remove(mealOptional.get());
                dietPlanService.updateDietPlan(dietPlan);
                return new ResponseEntity<>(dietPlan, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
