package com.GrowWithMe.GrowWithMe.controller;

import com.GrowWithMe.GrowWithMe.model.Exercise;
import com.GrowWithMe.GrowWithMe.model.TrainingPlan;
import com.GrowWithMe.GrowWithMe.service.impl.ExerciseService;
import com.GrowWithMe.GrowWithMe.service.impl.TrainingPlanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private TrainingPlanService trainingPlanService;

    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercise(){
        List<Exercise> exerciseList=exerciseService.getAllExercise();
        return new ResponseEntity<>(exerciseList, exerciseList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Integer id){
        Optional<Exercise> exerciseOptional= exerciseService.getExerciseById(id);
        return exerciseOptional.map(exercise -> new ResponseEntity<>(exercise, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<Exercise> createExerciseEntity(@RequestBody Exercise exercise){
        Exercise createdExercise=exerciseService.createExerciseEntity(exercise);
        return new ResponseEntity<>(createdExercise,HttpStatus.CREATED);
    }
    @PostMapping("/{trainingPlanId}")
    public ResponseEntity<Exercise> createExerciseEntity(@RequestBody Exercise exercise,@PathVariable Integer trainingPlanId){
        Exercise createdExercise=exerciseService.createExerciseEntity(exercise);
        Optional<TrainingPlan> trainingPlanOptional = trainingPlanService.getTrainingPlanById(trainingPlanId);
        if(trainingPlanOptional.isPresent())
        {
            TrainingPlan trainingPlan = trainingPlanOptional.get();
            trainingPlan.getExerciseList().add(createdExercise);
            trainingPlan.setExerciseList(trainingPlan.getExerciseList());
            trainingPlanService.updateTrainingPlan(trainingPlan);

            return new ResponseEntity<>(createdExercise,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExerciseEntity(@PathVariable Integer id){
        try {
            exerciseService.deleteExerciseEntity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping
    public ResponseEntity<Exercise> updateExercise(@RequestBody Exercise exerciseToUpdate){
        try {
            Exercise updatedExercise = exerciseService.updateExercise(exerciseToUpdate);
            return new ResponseEntity<>(updatedExercise, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
