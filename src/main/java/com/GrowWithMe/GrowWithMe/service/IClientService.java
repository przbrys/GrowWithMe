package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.*;

import java.util.List;
import java.util.Optional;

public interface IClientService {
    List<Client> getAllClient();
    Optional<Client> getClientById(Integer clientId);
    Optional<Trainer> getClientTrainer(Integer clientId);
    List<BodyInformation> getClientBodyInformation(Client client);
    List<Report> getClientReport(Client client);
    List<Survey> getClientSurvey(Client client);
    List<DietPlan> getClientDietPlan(Client client);
    List<TrainingPlan> getClientTrainingPlan(Client client);
    void deleteClientEntity(Integer clientId);
    Client createClientEntity(Client client);
    Client updateClient(Client clientToUpdate);
    Optional<User> getClientInfoFromUser(Integer clientId);
}
