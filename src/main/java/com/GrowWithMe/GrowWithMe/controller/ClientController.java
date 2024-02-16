package com.GrowWithMe.GrowWithMe.controller;

import com.GrowWithMe.GrowWithMe.model.*;
import com.GrowWithMe.GrowWithMe.service.impl.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> getAllClient() {
        List<Client> clientList=clientService.getAllClient();
        return new ResponseEntity<>(clientList, clientList.isEmpty()? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer id){
        Optional<Client> clientOptional =clientService.getClientById(id);
        return clientOptional.map(client -> new ResponseEntity<>(client,HttpStatus.OK)).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/{id}/clientTrainer")
    public ResponseEntity<Trainer> getClientTrainer(@PathVariable Integer id){
        Optional<Trainer> trainerOptional=clientService.getClientTrainer(id);
        return trainerOptional.map(trainer -> new ResponseEntity<>(trainer,HttpStatus.OK)).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/clientBodyInformation")
    public ResponseEntity<List<BodyInformation>> getClientBodyInformation(@RequestBody Client client){
        List<BodyInformation> bodyInformationList=clientService.getClientBodyInformation(client);
        return new ResponseEntity<>(bodyInformationList, bodyInformationList.isEmpty()? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }
    @GetMapping("/clientReport")
    public ResponseEntity<List<Report>> getClientReports(@RequestBody Client client){
        List<Report> reportList=clientService.getClientReport(client);
        return new ResponseEntity<>(reportList, reportList.isEmpty()? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }
    @GetMapping("/clientSurvey")
    public  ResponseEntity<List<Survey>> getClientSurvey(@RequestBody Client client){
        List<Survey> surveyList=clientService.getClientSurvey(client);
        return new ResponseEntity<>(surveyList, surveyList.isEmpty()? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }
    @GetMapping("/clientDietPlan")
    public ResponseEntity<List<DietPlan>> getClientDietPlan(@RequestBody Client client){
        List<DietPlan> dietPlanList=clientService.getClientDietPlan(client);
        return new ResponseEntity<>(dietPlanList, dietPlanList.isEmpty()? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }
    @GetMapping("/clientTrainingPlan")
    public ResponseEntity<List<TrainingPlan>> getClientTrainingPlan(@RequestBody Client client){
        List<TrainingPlan> trainingPlanList=clientService.getClientTrainingPlan(client);
        return new ResponseEntity<>(trainingPlanList, trainingPlanList.isEmpty()? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientEntity(@PathVariable Integer id){
        try {
            clientService.deleteClientEntity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<Client> createClientEntity(@RequestBody Client client){
        try {
            Client clientToCreate = clientService.createClientEntity(client);
            return new ResponseEntity<>(clientToCreate, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/updateClient")
    public ResponseEntity<Client> updateClient(@RequestBody Client clientToUpdate){
        try {
            Client updatedClient=clientService.updateClient(clientToUpdate);
            return new ResponseEntity<>(updatedClient,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}/clientInfoFromUser")
    public ResponseEntity<User> getClientInfoFromUser(@PathVariable Integer id){
        Optional<User> userOptional=clientService.getClientInfoFromUser(id);
        return userOptional.map(user -> new ResponseEntity<>(user,HttpStatus.OK)).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
