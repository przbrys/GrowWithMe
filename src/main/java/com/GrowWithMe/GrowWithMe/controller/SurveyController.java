package com.GrowWithMe.GrowWithMe.controller;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.DTO.ExistingEntryToClientDTO;
import com.GrowWithMe.GrowWithMe.model.DTO.ExistingQuestionToSurveyDTO;
import com.GrowWithMe.GrowWithMe.model.Question;
import com.GrowWithMe.GrowWithMe.model.Survey;
import com.GrowWithMe.GrowWithMe.service.impl.ClientService;
import com.GrowWithMe.GrowWithMe.service.impl.QuestionService;
import com.GrowWithMe.GrowWithMe.service.impl.SurveyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/survey")
public class SurveyController {
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<Survey>> getAllSurvey() {
        List<Survey> surveyList = surveyService.getAllSurvey();
        return new ResponseEntity<>(surveyList, surveyList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable Integer id) {
        Optional<Survey> surveyOptional = surveyService.getSurveyById(id);
        return surveyOptional.map(survey -> new ResponseEntity<>(survey, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{trainerId}/trainerSurveys")
    public ResponseEntity<List<Survey>> getSurveysByTrainerId(@PathVariable Integer trainerId) {
        List<Survey> surveyList = surveyService.getSurveyByTrainerId(trainerId);
        return new ResponseEntity<>(surveyList, surveyList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Survey> createSurveyEntity(@RequestBody Survey survey) {
        try {
            Survey surveyToCreate = surveyService.createSurveyEntity(survey);
            return new ResponseEntity<>(surveyToCreate, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @PostMapping("/surveyToNewUser")
    public ResponseEntity<Survey> createSurveyToNewUser(@RequestBody ExistingEntryToClientDTO existingSurveyToClientDTO) {
        try {
            Optional<Survey> surveyOptional = surveyService.getSurveyById(existingSurveyToClientDTO.getEntryId());
            Optional<Client> clientOptional = clientService.getClientById(existingSurveyToClientDTO.getNewUserId());
            if (surveyOptional.isPresent() && clientOptional.isPresent()) {
                Survey survey = new Survey();
                survey.setClient(clientOptional.get());
                List<Question> questionList = new ArrayList<>();

                for (Question question : surveyOptional.get().getQuestionList()) {
                    Question question1 = new Question(question.getQuestionContent());
                    questionService.createQuestionEntity(question1);
                    questionList.add(question1);
                }

                survey.setQuestionList(questionList);
                survey.setSurveyName(surveyOptional.get().getSurveyName());
                surveyService.createSurveyEntity(survey);
                return new ResponseEntity<>(survey, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurveyEntity(@PathVariable Integer id) {
        try {
            surveyService.deleteSurveyEntity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping
    public ResponseEntity<Survey> updateSurvey(@RequestBody Survey surveyToUpdate) {
        try {
            Survey updatedSurvey = surveyService.updateSurvey(surveyToUpdate);
            return new ResponseEntity<>(updatedSurvey, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/questionToSurvey")
    public ResponseEntity<Survey> addQuestionToSurvey(@RequestBody ExistingQuestionToSurveyDTO existingQuestionToSurveyDTO) {
        try {
            Optional<Question> questionOptional = questionService.getQuestionById(existingQuestionToSurveyDTO.getQuestionId());
            Optional<Survey> surveyOptional = surveyService.getSurveyById(existingQuestionToSurveyDTO.getSurveyId());
            if (questionOptional.isPresent() && surveyOptional.isPresent()) {
                Survey survey = surveyOptional.get();
                var questions = survey.getQuestionList();
                Question question = new Question(questionOptional.get().getQuestionContent());
                questions.add(questionService.createQuestionEntity(question));
                survey.setQuestionList(questions);
                surveyService.updateSurvey(survey);
                return new ResponseEntity<>(survey, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/deleteQuestionFromSurveyList")
    public ResponseEntity<Survey> deleteQuestionFromSurveyList(@RequestBody ExistingQuestionToSurveyDTO existingQuestionToSurveyDTO) {
        try {
            Optional<Survey> surveyOptional = surveyService.getSurveyById(existingQuestionToSurveyDTO.getSurveyId());
            Optional<Question> questionOptional = questionService.getQuestionById(existingQuestionToSurveyDTO.getQuestionId());
            if (surveyOptional.isPresent() && questionOptional.isPresent()) {
                Survey survey = surveyOptional.get();
                survey.getQuestionList().remove(questionOptional.get());
                surveyService.updateSurvey(survey);
                return new ResponseEntity<>(survey, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}