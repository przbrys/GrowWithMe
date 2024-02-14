package com.GrowWithMe.GrowWithMe.service.impl;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Trainer;
import com.GrowWithMe.GrowWithMe.repository.ITrainerRepository;
import com.GrowWithMe.GrowWithMe.service.ITrainerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerService implements ITrainerService {
    @Autowired
    private ITrainerRepository trainerRepository;

    @Override
    public List<Trainer> getAllTrainer() {
        return trainerRepository.findAll();
    }

    @Override
    public List<Client> getTrainerClient(Integer trainerId) {
        Optional<Trainer> trainerOptional=trainerRepository.findById(trainerId);
        if(trainerOptional.isPresent()){
            Trainer trainer = trainerOptional.get();
            return trainer.getClientList();
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Optional<Trainer> getTrainerById(Integer trainerId) {
        return trainerRepository.findById(trainerId);
    }

    @Override
    public void deleteTrainerById(Integer trainerId) {
        try {
            trainerRepository.deleteById(trainerId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Trainer entity with id " + trainerId + " not found", e);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Trainer", e);
        }
    }

    @Override
    public Trainer createTrainerEntity(Trainer trainer) {
        try {
            trainerRepository.save(trainer);
            return trainer;
        } catch (Exception e) {
            throw new RuntimeException("Error in creating new Trainer.", e);
        }
    }

    @Override
    public Trainer updateTrainer(Trainer trainerToUpdate) {
        Optional<Trainer> trainerOptional = trainerRepository.findById(trainerToUpdate.getTrainerId());
        if (trainerOptional.isPresent()) {
            return trainerRepository.save(trainerToUpdate);
        } else {
            throw new EntityNotFoundException("Trainer entity with id " + trainerToUpdate.getTrainerId() + " not found, update failed");
        }
    }
}
