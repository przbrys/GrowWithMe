package com.GrowWithMe.GrowWithMe.controller;
import com.GrowWithMe.GrowWithMe.model.Meal;
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

    @GetMapping
    public ResponseEntity<List<Meal>> getAllMeal(){
        List<Meal> mealList=mealService.getAllMeal();
        return new ResponseEntity<>(mealList, mealList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable Integer id){
        Optional<Meal> mealOptional= mealService.getMealById(id);
        return mealOptional.map(meal -> new ResponseEntity<>(meal, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<Meal> createNewMealEntity(@RequestBody Meal meal){
        Meal createdMeal = mealService.createMealEntity(meal);
        return new ResponseEntity<>(createdMeal,HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealById(@PathVariable Integer id){
        try {
            mealService.deleteMealEntity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping
    public ResponseEntity<Meal> updateMeal(@RequestBody Meal mealToUpdate){
        try {
            Meal updatedMeal = mealService.updateMealInformation(mealToUpdate);
            return new ResponseEntity<>(updatedMeal, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
