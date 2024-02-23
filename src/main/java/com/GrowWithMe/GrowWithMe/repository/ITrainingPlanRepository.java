package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.TrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITrainingPlanRepository extends JpaRepository<TrainingPlan, Integer> {
    List<TrainingPlan> findByClient(Client client);

    @Query("SELECT tp FROM TrainingPlan tp " +
            "JOIN tp.client c " +
            "JOIN c.trainer t " +
            "WHERE t.trainerId = :trainerId")
    List<TrainingPlan> getTrainingPlansByTrainerId(@Param("trainerId") Integer trainerId);


}
