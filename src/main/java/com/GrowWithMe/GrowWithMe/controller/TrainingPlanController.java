package com.GrowWithMe.GrowWithMe.controller;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Exercise;
import com.GrowWithMe.GrowWithMe.model.TrainingPlan;
import com.GrowWithMe.GrowWithMe.service.impl.TrainingPlanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/TrainingPlan")
public class TrainingPlanController {
    @Autowired
    private TrainingPlanService trainingPlanService;

    @GetMapping("/allTrainingPlan")
    public ResponseEntity<List<TrainingPlan>> getAllTrainingPlan(){
        List<TrainingPlan> trainingPlanList=trainingPlanService.getAllTrainingPlan();
        return new ResponseEntity<>(trainingPlanList, trainingPlanList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
//    @GetMapping("/allClientTrainingPlan")
//    public ResponseEntity<List<TrainingPlan>> getAllClientTrainingPlan(@RequestBody Client client){
//        List<TrainingPlan> trainingPlanList=trainingPlanService.getAllClientTrainingPlan(client);
//        return new ResponseEntity<>(trainingPlanList, trainingPlanList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<TrainingPlan> getTrainingPlanById(@PathVariable Integer id){
        Optional<TrainingPlan> trainingPlanOptional= trainingPlanService.getTrainingPlanById(id);
        return trainingPlanOptional.map(trainingPlan -> new ResponseEntity<>(trainingPlan, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/create")
    public ResponseEntity<TrainingPlan> createTrainingPlanEntity(@RequestBody TrainingPlan trainingPlan, @RequestBody List<Exercise> exerciseList){
        try{
            TrainingPlan trainingPlanToCreate=trainingPlanService.createTrainingPlanEntity(trainingPlan, exerciseList);
            return new ResponseEntity<>(trainingPlanToCreate,HttpStatus.CREATED);
        }catch(IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainingPlanEntity(@PathVariable Integer id){
        try {
            trainingPlanService.deleteTrainingPlanEntity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/updateTrainingPlan")
    public ResponseEntity<TrainingPlan> updateTrainingPlan(@RequestBody TrainingPlan trainingPlanToUpdate){
        try {
            TrainingPlan updatedTrainingPlan = trainingPlanService.updateTrainingPlan(trainingPlanToUpdate);
            return new ResponseEntity<>(updatedTrainingPlan, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}