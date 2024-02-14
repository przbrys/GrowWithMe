package com.GrowWithMe.GrowWithMe.service.impl;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Question;
import com.GrowWithMe.GrowWithMe.model.Survey;
import com.GrowWithMe.GrowWithMe.repository.ISurveyRepository;
import com.GrowWithMe.GrowWithMe.service.ISurveyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService implements ISurveyService{
    @Autowired
    private ISurveyRepository surveyRepository;

    @Override
    public List<Survey> getAllSurvey() {
        return surveyRepository.findAll();
    }

//    @Override
//    public List<Survey> getAllClientSurvey(Client client) {
//        return surveyRepository.findByClient(client);
//    }

    @Override
    public Optional<Survey> getSurveyById(Integer surveyId) {
        return surveyRepository.findById(surveyId);
    }

    @Override
    public void deleteSurveyEntity(Integer surveyId) {
        try {
            surveyRepository.deleteById(surveyId);
        }catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Survey entity with id " + surveyId + " not found", e);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Survey", e);
        }
    }

    @Override
    public Survey createSurveyEntity(Survey survey, List<Question> questionList) {
        try {
            survey.setQuestionList(questionList);
            surveyRepository.save(survey);
            return survey;
        }catch (Exception e){
            throw new RuntimeException("Error in creating new Survey.",e);
        }
    }

    @Override
    public Survey updateSurvey(Survey surveyToUpdate) {
        Optional<Survey> surveyOptional =surveyRepository.findById(surveyToUpdate.getSurveyId());
        if (surveyOptional.isPresent()) {
            return surveyRepository.save(surveyToUpdate);
        } else {
            throw new EntityNotFoundException("Survey entity with id " + surveyToUpdate.getSurveyId() + " not found, update failed");
        }
    }
}