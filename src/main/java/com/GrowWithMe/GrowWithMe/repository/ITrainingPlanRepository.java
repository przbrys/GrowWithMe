package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.TrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrainingPlanRepository extends JpaRepository<TrainingPlan, Integer> {
}
