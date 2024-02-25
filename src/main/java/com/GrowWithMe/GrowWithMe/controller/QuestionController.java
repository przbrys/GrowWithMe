package com.GrowWithMe.GrowWithMe.controller;
import com.GrowWithMe.GrowWithMe.model.DietPlan;
import com.GrowWithMe.GrowWithMe.model.Meal;
import com.GrowWithMe.GrowWithMe.model.Question;
import com.GrowWithMe.GrowWithMe.model.Survey;
import com.GrowWithMe.GrowWithMe.service.impl.QuestionService;
import com.GrowWithMe.GrowWithMe.service.impl.SurveyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private SurveyService surveyService;
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestion(){
        List<Question> questionList= questionService.getAllQuestion();
        return new ResponseEntity<>(questionList, questionList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Integer id){
        Optional<Question> questionOptional = questionService.getQuestionById(id);
        return questionOptional.map(question -> new ResponseEntity<>(question, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Question> createQuestionEntity(@RequestBody Question question){
        Question createdQuestion=questionService.createQuestionEntity(question);
        return new ResponseEntity<>(createdQuestion,HttpStatus.CREATED);
    }

    @PostMapping("/{surveyId}")
    public ResponseEntity<Question> createNewQuestionEntity(@RequestBody Question question, @PathVariable Integer surveyId){
        Question createdQuestion = questionService.createQuestionEntity(question);
        Optional<Survey> surveyOptional= surveyService.getSurveyById(surveyId);
        if(surveyOptional.isPresent())
        {
            Survey survey= surveyOptional.get();
            survey.getQuestionList().add(createdQuestion);
            survey.setQuestionList(survey.getQuestionList());
            surveyService.updateSurvey(survey);

            return new ResponseEntity<>(createdQuestion,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestionEntity(@PathVariable Integer id){
        try {
            questionService.deleteQuestionEntity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping
    public ResponseEntity<Question> updateQuestion(@RequestBody Question questionToUpdate){
        try {
            Question updatedQuestion = questionService.updateQuestion(questionToUpdate);
            return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
