package com.GrowWithMe.GrowWithMe.controller;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.DTO.ExistingExerciseToTrainingPlanDTO;
import com.GrowWithMe.GrowWithMe.model.DTO.ExistingEntryToClientDTO;
import com.GrowWithMe.GrowWithMe.model.Exercise;
import com.GrowWithMe.GrowWithMe.model.TrainingPlan;
import com.GrowWithMe.GrowWithMe.service.impl.ClientService;
import com.GrowWithMe.GrowWithMe.service.impl.ExerciseService;
import com.GrowWithMe.GrowWithMe.service.impl.TrainingPlanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trainingPlan")
public class TrainingPlanController {
    @Autowired
    private TrainingPlanService trainingPlanService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<TrainingPlan>> getAllTrainingPlan(){
        List<TrainingPlan> trainingPlanList=trainingPlanService.getAllTrainingPlan();
        return new ResponseEntity<>(trainingPlanList, trainingPlanList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TrainingPlan> getTrainingPlanById(@PathVariable Integer id){
        Optional<TrainingPlan> trainingPlanOptional= trainingPlanService.getTrainingPlanById(id);
        return trainingPlanOptional.map(trainingPlan -> new ResponseEntity<>(trainingPlan, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/{id}/trainerPlans")
    public ResponseEntity<List<TrainingPlan>> getTrainingPlansByTrainerId(@PathVariable Integer id){
        List<TrainingPlan> trainingPlanList=trainingPlanService.getTrainingPlansByTrainerId(id);
        return new ResponseEntity<>(trainingPlanList, trainingPlanList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<TrainingPlan> createTrainingPlanEntity(@RequestBody TrainingPlan trainingPlan){
        try{
            TrainingPlan trainingPlanToCreate=trainingPlanService.createTrainingPlanEntity(trainingPlan);
            return new ResponseEntity<>(trainingPlanToCreate,HttpStatus.CREATED);
        }catch(IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // istniejące ćwiczenie do istniejącego planu treningowego
    // potem istniejąca dieta do istniejącego klienta
    // istniejący posiłek do istniejącej diety,
    // istniejąco ankieta do istniejącego użytkownika (w przypadku ankeity dodatkowy krok, trzeba wyczyścic odpowiedzi użytkownika, te same pytania bez odpowiedzi)
    @PostMapping("/trainingPlanToNewUser")
    public ResponseEntity<TrainingPlan> createTrainingPlanToNewUser(@RequestBody ExistingEntryToClientDTO existingTrainingPlanToClientDTO){
        try{
            Optional<TrainingPlan> trainingPlanOptional=trainingPlanService.getTrainingPlanById(existingTrainingPlanToClientDTO.getEntryId());
            Optional<Client> clientOptional = clientService.getClientById(existingTrainingPlanToClientDTO.getNewUserId());
            if (trainingPlanOptional.isPresent()&& clientOptional.isPresent())
            {
                TrainingPlan trainingPlan = new TrainingPlan();
                trainingPlan.setClient(clientOptional.get());
                List<Exercise> exerciseList = new ArrayList<>(trainingPlanOptional.get().getExerciseList());
                trainingPlan.setExerciseList(exerciseList);
                trainingPlan.setTrainingPlanName(trainingPlanOptional.get().getTrainingPlanName());
                trainingPlanService.createTrainingPlanEntity(trainingPlan);
                return new ResponseEntity<>(trainingPlan,HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
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
    @PatchMapping
    public ResponseEntity<TrainingPlan> updateTrainingPlan(@RequestBody TrainingPlan trainingPlanToUpdate){
        try {
            TrainingPlan updatedTrainingPlan = trainingPlanService.updateTrainingPlan(trainingPlanToUpdate);
            return new ResponseEntity<>(updatedTrainingPlan, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/exerciseToTrainingPlan")
    public ResponseEntity<TrainingPlan> addExerciseToTrainingPlan(@RequestBody ExistingExerciseToTrainingPlanDTO existingExerciseToTrainingPlan){
        try{
            Optional <Exercise> exerciseOptional = exerciseService.getExerciseById(existingExerciseToTrainingPlan.getExerciseId());
            Optional<TrainingPlan> trainingPlanOptional = trainingPlanService.getTrainingPlanById(existingExerciseToTrainingPlan.getTrainingPlanId());
            if (exerciseOptional.isPresent()&& trainingPlanOptional.isPresent())
            {
                TrainingPlan trainingPlan = trainingPlanOptional.get();
                var exercises = trainingPlan.getExerciseList();
                Exercise exercise = new Exercise(exerciseOptional.get());
                exercises.add(exerciseService.createExerciseEntity(exercise));
                trainingPlan.setExerciseList(exercises);
                trainingPlanService.updateTrainingPlan(trainingPlan);
                return new ResponseEntity<>(trainingPlan, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/deleteExerciseFromTrainingPlanList")
    public ResponseEntity<TrainingPlan> deleteExerciseFromTrainingPlanList(@RequestBody ExistingExerciseToTrainingPlanDTO existingExerciseToTrainingPlanDTO){
       try {
            Optional<TrainingPlan> trainingPlanOptional = trainingPlanService.getTrainingPlanById(existingExerciseToTrainingPlanDTO.getTrainingPlanId());
            Optional<Exercise> exerciseOptional = exerciseService.getExerciseById(existingExerciseToTrainingPlanDTO.getExerciseId());
            if (exerciseOptional.isPresent() && trainingPlanOptional.isPresent()) {
                TrainingPlan trainingPlan = trainingPlanOptional.get();
                trainingPlan.getExerciseList().remove(exerciseOptional.get());
                trainingPlanService.updateTrainingPlan(trainingPlan);
                return new ResponseEntity<>(trainingPlan, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (IllegalArgumentException e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

}