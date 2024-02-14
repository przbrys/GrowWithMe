package com.GrowWithMe.GrowWithMe.controller;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Trainer;
import com.GrowWithMe.GrowWithMe.service.impl.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Trainer")
public class TrainerController {
    @Autowired
    private TrainerService trainerService;

    @GetMapping("/allTrainer")
    public ResponseEntity<List<Trainer>> getAllTrainers() {
        List<Trainer> trainerList = trainerService.getAllTrainer();
        return new ResponseEntity<>(trainerList, trainerList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/{id}/trainerClient")
    public ResponseEntity<List<Client>> getTrainerClient(@PathVariable Integer id) {
        List<Client> clientList = trainerService.getTrainerClient(id);
        return new ResponseEntity<>(clientList, clientList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable Integer id) {
        Optional<Trainer> trainerOptional = trainerService.getTrainerById(id);
        return trainerOptional.map(trainer -> new ResponseEntity<>(trainer, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Trainer> createTrainerEntity(@RequestBody Trainer trainer) {
        try {
            Trainer trainerToCreate = trainerService.createTrainerEntity(trainer);
            return new ResponseEntity<>(trainerToCreate, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainerEntity(@PathVariable Integer id){
        try {
            trainerService.deleteTrainerById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/updateTrainer")
    public ResponseEntity<Trainer> updateTrainer(@RequestBody Trainer trainerToUpdate){
        try {
            Trainer updatedTrainer = trainerService.updateTrainer(trainerToUpdate);
            return new ResponseEntity<>(updatedTrainer, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
