package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.DietPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDietPlanRepository extends JpaRepository<DietPlan, Integer> {
    List<DietPlan> findByClient(Client client);
}
