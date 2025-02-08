package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.*;
import com.GrowWithMe.GrowWithMe.repository.*;
import com.GrowWithMe.GrowWithMe.service.impl.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    private static final int CLIENT_ID = 1;
    private static final Role ROLE_CLIENT = new Role(1, "CLIENT");
    private static final Role ROLE_TRAINER = new Role(2, "TRAINER");

    private static final User USER_TRAINER = new User(3, "login", "name", "surname", "password", Set.of(ROLE_CLIENT));
    private static final User USER_CLIENT = new User(4, "login2", "name2", "surname2", "password2", Set.of(ROLE_TRAINER));
    private static final Trainer TRAINER = new Trainer(5, 123456789, USER_TRAINER, emptyList());
    private static final Client CLIENT = new Client(CLIENT_ID, 185, 123456789, USER_CLIENT, TRAINER, emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
    private static final BodyInformation BODY_INFORMATION = new BodyInformation(1, 100, 20, 110, 60, 35, 90, 35, "additionalInfo", CLIENT);
    private static final DietPlan DIET_PLAN = generateDietPlan();
    private static final TrainingPlan TRAINING_PLAN = generateTrainingPlan();
    private static final Survey SURVEY = generateSurvey();
    private static final Report REPORT = generateReport();

    @Mock
    private IClientRepository clientRepository;
    @Mock
    private IBodyInformationRepository bodyInformationRepository;
    @Mock
    private IDietPlanRepository dietPlanRepository;
    @Mock
    private ITrainingPlanRepository trainingPlanRepository;
    @Mock
    private ISurveyRepository surveyRepository;
    @Mock
    private IReportRepository reportRepository;

    @InjectMocks
    private ClientService tested;

    @Test
    void getAllClient() {
        //given
        List<Client> clients = List.of(CLIENT);

        //when
        when(clientRepository.findAll()).thenReturn(clients);
        List<Client> result = tested.getAllClient();

        //then
        assertThat(result).isEqualTo(clients);
    }

    @Test
    void getClientById__1() {
        //given //when
        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(CLIENT));
        Optional<Client> result = tested.getClientById(CLIENT_ID);

        //then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(CLIENT);
    }

    @Test
    void getClientById__2() {
        //given //when
        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.empty());
        Optional<Client> result = tested.getClientById(CLIENT_ID);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void getClientTrainer__1() {
        //given //when
        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(CLIENT));
        Optional<Trainer> result = tested.getClientTrainer(CLIENT_ID);

        //then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(TRAINER);
    }

    @Test
    void getClientTrainer__2() {
        //given
        Client clientWithoutTrainer = new Client(CLIENT_ID, 185, 123456789, USER_CLIENT, null, emptyList(), emptyList(), emptyList(), emptyList(), emptyList());

        //when
        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(clientWithoutTrainer));
        Optional<Trainer> result = tested.getClientTrainer(CLIENT_ID);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void getClientBodyInformation() {
        //given
        List<BodyInformation> bodyInformations = List.of(BODY_INFORMATION);

        //when
        when(bodyInformationRepository.findByClient(CLIENT)).thenReturn(bodyInformations);
        List<BodyInformation> result = tested.getClientBodyInformation(CLIENT);

        //then
        assertThat(result).isEqualTo(bodyInformations);
    }

    @Test
    void getClientReport() {
        //given
        List<Report> reports = List.of(REPORT);

        //when
        when(reportRepository.findByClient(CLIENT)).thenReturn(reports);
        List<Report> result = tested.getClientReport(CLIENT);

        //then
        assertThat(result).isEqualTo(reports);
    }

    @Test
    void getClientSurvey() {
        //given
        List<Survey> surveys = List.of(SURVEY);

        //when
        when(surveyRepository.findByClient(CLIENT)).thenReturn(surveys);
        List<Survey> result = tested.getClientSurvey(CLIENT);

        //then
        assertThat(result).isEqualTo(surveys);
    }

    @Test
    void getClientDietPlan() {
        //given
        List<DietPlan> dietPlans = List.of(DIET_PLAN);

        //when
        when(dietPlanRepository.findByClient(CLIENT)).thenReturn(dietPlans);
        List<DietPlan> result = tested.getClientDietPlan(CLIENT);

        //then
        assertThat(result).isEqualTo(dietPlans);
    }

    @Test
    void getClientTrainingPlan() {
        //given
        List<TrainingPlan> trainingPlans = List.of(TRAINING_PLAN);

        //when
        when(trainingPlanRepository.findByClient(CLIENT)).thenReturn(trainingPlans);
        List<TrainingPlan> result = tested.getClientTrainingPlan(CLIENT);

        //then
        assertThat(result).isEqualTo(trainingPlans);
    }


    @Test
    void deleteClientEntity__1() {
        //given //when
        tested.deleteClientEntity(CLIENT_ID);

        //then
        verify(clientRepository).deleteById(CLIENT_ID);
    }

    @Test
    void deleteClientEntity__2() {
        //given //when //then
        doThrow(EmptyResultDataAccessException.class).when(clientRepository).deleteById(CLIENT_ID);

        assertThatThrownBy(() -> tested.deleteClientEntity(CLIENT_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Client entity with id " + CLIENT_ID + " not found");
    }

    @Test
    void deleteClientEntity__3() {
        //given //when //then
        doThrow(RuntimeException.class).when(clientRepository).deleteById(CLIENT_ID);

        assertThatThrownBy(() -> tested.deleteClientEntity(CLIENT_ID))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deleting Client");
    }

    @Test
    void createClientEntity__1() {
        //given //when
        Client result = tested.createClientEntity(CLIENT);

        //then
        verify(clientRepository).save(CLIENT);
        assertThat(result).isEqualTo(CLIENT);
    }

    @Test
    void createClientEntity__2() {
        //given //when //then
        doThrow(RuntimeException.class).when(clientRepository).save(CLIENT);

        assertThatThrownBy(() -> tested.createClientEntity(CLIENT))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error in creating new Client.");
    }

    private static DietPlan generateDietPlan() {
        DietPlan dietPlan = new DietPlan();
        dietPlan.setDietPlanId(1);
        dietPlan.setDietPlanName("name");
        dietPlan.setClient(CLIENT);

        List<Meal> meals = new ArrayList<>();
        Meal meal1 = new Meal();
        meal1.setMealName("Śniadanie");
        meals.add(meal1);
        dietPlan.setMealList(meals);

        return dietPlan;
    }

    private static TrainingPlan generateTrainingPlan() {
        TrainingPlan trainingPlan = new TrainingPlan();
        trainingPlan.setTrainingPlanId(1);
        trainingPlan.setTrainingPlanName("name");
        trainingPlan.setClient(CLIENT);

        List<Exercise> exercises = new ArrayList<>();
        Exercise exercise1 = new Exercise();
        exercise1.setExerciseName("Przysiady");
        exercises.add(exercise1);
        trainingPlan.setExerciseList(exercises);

        return trainingPlan;
    }

    private static Survey generateSurvey() {
        Survey survey = new Survey();
        survey.setSurveyId(1);
        survey.setSurveyName("name");
        survey.setClient(CLIENT);

        List<Question> questions = new ArrayList<>();
        Question question1 = new Question("Jak się czujesz?");
        questions.add(question1);
        survey.setQuestionList(questions);

        return survey;
    }

    private static Report generateReport() {
        Report report = new Report();
        report.setReportId(1);
        report.setReportClientMessage("name");
        report.setReportDate("2024-07-27");
        report.setClient(CLIENT);

        return report;
    }
}

