package com.GrowWithMe.GrowWithMe.controller;

import com.GrowWithMe.GrowWithMe.model.DietPlan;
import com.GrowWithMe.GrowWithMe.service.impl.DietPlanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/DietPlan")
public class DietPlanController {
    @Autowired
    private DietPlanService dietPlanService;
    @GetMapping
    public ResponseEntity<List<DietPlan>> getAllDietPlan(){
        List<DietPlan> dietPlanList=dietPlanService.getAllDietPlan();
        return new ResponseEntity<>(dietPlanList, dietPlanList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);

    }
//    @GetMapping("/allClientDietPlan")
//    public ResponseEntity<List<DietPlan>> getAllClientDietPlan(@RequestBody Client client){
//        List<DietPlan> dietPlanList=dietPlanService.getAllClientDietPlan(client);
//        return new ResponseEntity<>(dietPlanList, dietPlanList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<DietPlan> getDietPlanById(@PathVariable Integer id){
        Optional<DietPlan> dietPlanOptional= dietPlanService.getDietPlanById(id);
        return dietPlanOptional.map(dietPlan -> new ResponseEntity<>(dietPlan, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<DietPlan> createDietPlanEntity(@RequestBody DietPlan dietPlan){
        try{
            DietPlan dietPlanToCreate=dietPlanService.createDietPlanEntity(dietPlan);
            return new ResponseEntity<>(dietPlanToCreate,HttpStatus.CREATED);
        }catch(IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDietPlanEntity(@PathVariable Integer id){
        try {
            dietPlanService.deleteDietPlanEntity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping
    public ResponseEntity<DietPlan> updateDietPlan(@RequestBody DietPlan dietPlanToUpdate){
        try {
            DietPlan updatedDietPlan = dietPlanService.updateDietPlan(dietPlanToUpdate);
            return new ResponseEntity<>(updatedDietPlan, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
