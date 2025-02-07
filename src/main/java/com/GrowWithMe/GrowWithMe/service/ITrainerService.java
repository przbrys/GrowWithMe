package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface ITrainerService {
    List<Trainer> getAllTrainer();

    List<Client> getTrainerClient(Integer trainerId);

    Optional<Trainer> getTrainerById(Integer trainerId);

    void deleteTrainerById(Integer trainerId);

    Trainer createTrainerEntity(Trainer trainer);

    Trainer updateTrainer(Trainer trainerToUpdate);
}
