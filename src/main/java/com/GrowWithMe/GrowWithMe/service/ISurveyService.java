package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.Survey;

import java.util.List;
import java.util.Optional;

public interface ISurveyService {
    List<Survey> getAllSurvey();

    List<Survey> getSurveyByTrainerId(Integer trainerId);

    Optional<Survey> getSurveyById(Integer surveyId);

    void deleteSurveyEntity(Integer surveyId);

    Survey createSurveyEntity(Survey survey);

    Survey updateSurvey(Survey surveyToUpdate);
}
