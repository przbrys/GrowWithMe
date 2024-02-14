package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITrainerRepository extends JpaRepository<Trainer, Integer> {
}
