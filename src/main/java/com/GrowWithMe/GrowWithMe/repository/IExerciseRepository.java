package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExerciseRepository extends JpaRepository<Exercise, Integer> {
}
