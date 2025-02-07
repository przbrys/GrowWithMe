package com.GrowWithMe.GrowWithMe.service.impl;

import com.GrowWithMe.GrowWithMe.model.*;
import com.GrowWithMe.GrowWithMe.repository.*;
import com.GrowWithMe.GrowWithMe.service.IClientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements IClientService {
    @Autowired
    private IClientRepository clientRepository;
    @Autowired
    private IBodyInformationRepository bodyInformationRepository;
    @Autowired
    private IDietPlanRepository dietPlanRepository;
    @Autowired
    private ITrainingPlanRepository trainingPlanRepository;
    @Autowired
    private ISurveyRepository surveyRepository;
    @Autowired
    private IReportRepository reportRepository;

    @Override
    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(Integer clientId) {
        return clientRepository.findById(clientId);
    }

    @Override
    public Optional<Trainer> getClientTrainer(Integer clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent() && clientOptional.get().getTrainer() != null) {
            return Optional.of(clientOptional.get().getTrainer());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<BodyInformation> getClientBodyInformation(Client client) {
        return bodyInformationRepository.findByClient(client);
    }

    @Override
    public List<Report> getClientReport(Client client) {
        return reportRepository.findByClient(client);
    }

    @Override
    public List<Survey> getClientSurvey(Client client) {
        return surveyRepository.findByClient(client);
    }

    @Override
    public List<DietPlan> getClientDietPlan(Client client) {
        return dietPlanRepository.findByClient(client);
    }

    @Override
    public List<TrainingPlan> getClientTrainingPlan(Client client) {
        return trainingPlanRepository.findByClient(client);
    }

    @Override
    public void deleteClientEntity(Integer clientId) {
        try {
            clientRepository.deleteById(clientId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Client entity with id " + clientId + " not found", e);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Client", e);
        }
    }

    @Override
    public Client createClientEntity(Client client) {
        try {
            clientRepository.save(client);
            return client;
        } catch (Exception e) {
            throw new RuntimeException("Error in creating new Client.", e);
        }
    }

    @Override
    public Client updateClient(Client clientToUpdate) {
        Optional<Client> clientOptional = clientRepository.findById(clientToUpdate.getClientId());
        if (clientOptional.isPresent()) {
            return clientRepository.save(clientToUpdate);
        } else {
            throw new EntityNotFoundException("Client entity with id " + clientToUpdate.getClientId() + " not found, update failed");
        }
    }

    @Override
    public Optional<User> getClientInfoFromUser(Integer clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent() && clientOptional.get().getUser() != null) {
            return Optional.of(clientOptional.get().getUser());
        } else {
            return Optional.empty();
        }
    }
}
