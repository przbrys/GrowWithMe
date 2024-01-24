package com.GrowWithMe.GrowWithMe.service;
import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Question;
import com.GrowWithMe.GrowWithMe.model.Survey;

import java.util.List;
import java.util.Optional;

public interface ISurveyService {
    List<Survey> getAllSurvey();
    List<Survey> getAllClientSurvey(Client client);
    Optional<Survey> getSurveyById(Integer surveyId);
    void deleteSurveyEntity(Integer surveyId);
    Survey createSurveyEntity(Survey survey, List<Question> questionList);
    Survey updateSurvey(Survey surveyToUpdate);
}
