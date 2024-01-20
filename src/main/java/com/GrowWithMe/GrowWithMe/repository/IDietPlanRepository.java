package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.DietPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDietPlanRepository extends JpaRepository<DietPlan, Integer> {
}
