package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrainerRepository extends JpaRepository<Trainer, Integer> {
}
