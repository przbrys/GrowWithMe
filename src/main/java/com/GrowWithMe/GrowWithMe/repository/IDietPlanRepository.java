package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.DietPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDietPlanRepository extends JpaRepository<DietPlan, Integer> {
    List<DietPlan> findByClient(Client client);

    @Query("SELECT dp FROM DietPlan dp " +
            "JOIN dp.client c " +
            "JOIN c.trainer t " +
            "WHERE t.trainerId = :trainerId")
    List<DietPlan> getDietPlansByTrainerId(@Param("trainerId") Integer trainerId);
}
