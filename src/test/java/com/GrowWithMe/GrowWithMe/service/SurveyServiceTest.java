package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Question;
import com.GrowWithMe.GrowWithMe.model.Survey;
import com.GrowWithMe.GrowWithMe.repository.ISurveyRepository;
import com.GrowWithMe.GrowWithMe.service.impl.SurveyService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {

    private static final int SURVEY_ID = 1;
    private static final int TRAINER_ID = 2;
    private static final Client CLIENT = new Client();
    private static final Question QUESTION = new Question();
    private static final Survey SURVEY = generateSurvey();

    @Mock
    private ISurveyRepository surveyRepository;

    @InjectMocks
    private SurveyService tested;

    @Test
    void getAllSurvey() {
        //given
        List<Survey> surveys = List.of(SURVEY);

        //when
        when(surveyRepository.findAll()).thenReturn(surveys);
        List<Survey> result = tested.getAllSurvey();

        //then
        assertThat(result).isEqualTo(surveys);
    }

    @Test
    void getSurveyByTrainerId() {
        //given
        List<Survey> surveys = List.of(SURVEY);

        //when
        when(surveyRepository.getSurveyByTrainerId(TRAINER_ID)).thenReturn(surveys);
        List<Survey> result = tested.getSurveyByTrainerId(TRAINER_ID);

        //then
        assertThat(result).isEqualTo(surveys);
    }

    @Test
    void getSurveyById__1() {
        //given //when
        when(surveyRepository.findById(SURVEY_ID)).thenReturn(Optional.of(SURVEY));
        Optional<Survey> result = tested.getSurveyById(SURVEY_ID);

        //then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(SURVEY);
    }

    @Test
    void getSurveyById__2() {
        //given //when
        when(surveyRepository.findById(SURVEY_ID)).thenReturn(Optional.empty());
        Optional<Survey> result = tested.getSurveyById(SURVEY_ID);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void deleteSurveyEntity__1() {
        //given //when
        tested.deleteSurveyEntity(SURVEY_ID);

        //then
        verify(surveyRepository).deleteById(SURVEY_ID);
    }

    @Test
    void deleteSurveyEntity__2() {
        //given //when //then
        doThrow(EmptyResultDataAccessException.class).when(surveyRepository).deleteById(SURVEY_ID);

        assertThatThrownBy(() -> tested.deleteSurveyEntity(SURVEY_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Survey entity with id " + SURVEY_ID + " not found");
    }

    @Test
    void deleteSurveyEntity__3() {
        //given //when //then
        doThrow(RuntimeException.class).when(surveyRepository).deleteById(SURVEY_ID);

        assertThatThrownBy(() -> tested.deleteSurveyEntity(SURVEY_ID))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deleting Survey");
    }

    @Test
    void createSurveyEntity__1() {
        //given //when
        tested.createSurveyEntity(SURVEY);

        //then
        verify(surveyRepository).save(SURVEY);
    }

    @Test
    void createSurveyEntity__2() {
        //given //when //then
        doThrow(RuntimeException.class).when(surveyRepository).save(SURVEY);

        assertThatThrownBy(() -> tested.createSurveyEntity(SURVEY))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error in creating new Survey.");
    }

    @Test
    void updateSurvey__1() {
        //given
        Survey surveyToUpdate = new Survey();
        surveyToUpdate.setSurveyId(SURVEY_ID);
        surveyToUpdate.setSurveyName("Updated Survey");
        surveyToUpdate.setClient(CLIENT);
        surveyToUpdate.setQuestionList(emptyList());

        Survey expectedSurvey = generateSurvey();
        expectedSurvey.setSurveyId(SURVEY_ID);
        expectedSurvey.setSurveyName("Updated Survey");
        expectedSurvey.setClient(CLIENT);
        expectedSurvey.setQuestionList(emptyList());


        //when
        when(surveyRepository.findById(SURVEY_ID)).thenReturn(Optional.of(SURVEY));
        when(surveyRepository.save(any(Survey.class))).thenReturn(expectedSurvey);

        Survey result = tested.updateSurvey(surveyToUpdate);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getSurveyName()).isEqualTo("Updated Survey");
        assertThat(result.getClient()).isEqualTo(CLIENT);
        assertThat(result.getQuestionList()).isEqualTo(emptyList());
        verify(surveyRepository).save(any(Survey.class));
    }

    @Test
    void updateSurvey__2() {
        //given
        Survey surveyToUpdate = new Survey();
        surveyToUpdate.setSurveyId(SURVEY_ID);

        //when
        when(surveyRepository.findById(SURVEY_ID)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> tested.updateSurvey(surveyToUpdate))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Survey entity with id " + SURVEY_ID + " not found, update failed");
    }

    private static Survey generateSurvey() {
        Survey survey = new Survey();
        survey.setSurveyId(SURVEY_ID);
        survey.setSurveyName("Test Survey");
        survey.setClient(CLIENT);
        survey.setQuestionList(List.of(QUESTION));
        return survey;
    }
}