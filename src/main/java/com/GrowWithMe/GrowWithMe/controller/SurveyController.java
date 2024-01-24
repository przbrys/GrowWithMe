package com.GrowWithMe.GrowWithMe.controller;

import com.GrowWithMe.GrowWithMe.model.*;
import com.GrowWithMe.GrowWithMe.service.impl.SurveyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Survey")
public class SurveyController {
    @Autowired
    private SurveyService surveyService;

    @GetMapping("/allSurvey")
    public ResponseEntity<List<Survey>> getAllSurvey(){
        List<Survey> surveyList =surveyService.getAllSurvey();
        return new ResponseEntity<>(surveyList,surveyList.isEmpty()? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }

    @GetMapping("/allClientSurvey")
    public ResponseEntity<List<Survey>> getAllClientSurvey(Client client){
        List<Survey> surveyList=surveyService.getAllClientSurvey(client);
        return new ResponseEntity<>(surveyList,surveyList.isEmpty()? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Survey> createSurveyEntity(@RequestBody Survey survey,@RequestBody List<Question> questionList){
        try{
            Survey surveyToCreate=surveyService.createSurveyEntity(survey, questionList);
            return new ResponseEntity<>(surveyToCreate,HttpStatus.CREATED);
        }catch(IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurveyEntity(@PathVariable Integer id){
        try {
            surveyService.deleteSurveyEntity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/updateSurvey")
    public ResponseEntity<Survey> updateSurvey(@RequestBody Survey surveyToUpdate){
        try {
            Survey updatedSurvey = surveyService.updateSurvey(surveyToUpdate);
            return new ResponseEntity<>(updatedSurvey, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}