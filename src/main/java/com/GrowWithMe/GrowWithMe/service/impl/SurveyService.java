package com.GrowWithMe.GrowWithMe.service.impl;

import com.GrowWithMe.GrowWithMe.model.Question;
import com.GrowWithMe.GrowWithMe.model.Survey;
import com.GrowWithMe.GrowWithMe.repository.ISurveyRepository;
import com.GrowWithMe.GrowWithMe.service.ISurveyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SurveyService implements ISurveyService{
    @Autowired
    private ISurveyRepository surveyRepository;

    @Override
    public List<Survey> getAllSurvey() {
        return surveyRepository.findAll();
    }

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
    public Survey createSurveyEntity(Survey survey) {
        try {
            return surveyRepository.save(survey);
        }catch (Exception e){
            throw new RuntimeException("Error in creating new Survey.",e);
        }
    }

    @Override
    public Survey updateSurvey(Survey surveyToUpdate) {
        Optional<Survey> surveyOptional =surveyRepository.findById(surveyToUpdate.getSurveyId());
        if (surveyOptional.isPresent()) {
            Survey survey=surveyOptional.get();

            if (surveyToUpdate.getSurveyName()!=null && !surveyToUpdate.getSurveyName().equals(survey.getSurveyName()))
                survey.setSurveyName(surveyToUpdate.getSurveyName());
            if(surveyToUpdate.getClient()!=null && !Objects.equals(surveyToUpdate.getClient(), survey.getClient()))
                survey.setClient(surveyToUpdate.getClient());
            if(surveyToUpdate.getQuestionList()!=null && !Objects.deepEquals(surveyToUpdate.getQuestionList(), survey.getQuestionList()))
                survey.setQuestionList(surveyToUpdate.getQuestionList());

            return surveyRepository.save(survey);
        } else {
            throw new EntityNotFoundException("Survey entity with id " + surveyToUpdate.getSurveyId() + " not found, update failed");
        }
    }
}