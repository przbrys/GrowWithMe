package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMealRepository extends JpaRepository<Meal, Integer> {
}
