package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.TrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITrainingPlanRepository extends JpaRepository<TrainingPlan, Integer> {
    List<TrainingPlan> findByClient(Client client);
}
