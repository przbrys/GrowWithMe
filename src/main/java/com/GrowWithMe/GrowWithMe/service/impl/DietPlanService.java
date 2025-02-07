package com.GrowWithMe.GrowWithMe.service.impl;

import com.GrowWithMe.GrowWithMe.model.DietPlan;
import com.GrowWithMe.GrowWithMe.repository.IDietPlanRepository;
import com.GrowWithMe.GrowWithMe.service.IDietPlanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DietPlanService implements IDietPlanService {
    @Autowired
    private IDietPlanRepository dietPlanRepository;

    @Override
    public List<DietPlan> getAllDietPlan() {
        return dietPlanRepository.findAll();
    }

    @Override
    public List<DietPlan> getDietPlansByTrainerId(Integer trainerId) {
        return dietPlanRepository.getDietPlansByTrainerId(trainerId);
    }

    @Override
    public Optional<DietPlan> getDietPlanById(Integer dietPlanId) {
        return dietPlanRepository.findById(dietPlanId);
    }

    @Override
    public void deleteDietPlanEntity(Integer dietPlanId) {
        try {
            dietPlanRepository.deleteById(dietPlanId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("DietPlan entity with id " + dietPlanId + " not found", e);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting DietPlan", e);
        }
    }

    @Override
    public DietPlan createDietPlanEntity(DietPlan dietPlan) {
        try {
            return dietPlanRepository.save(dietPlan);
        } catch (Exception e) {
            throw new RuntimeException("Error in creating new DietPlan.", e);
        }
    }

    @Override
    public DietPlan updateDietPlan(DietPlan dietPlanToUpdate) {
        Optional<DietPlan> dietPlanOptional = dietPlanRepository.findById(dietPlanToUpdate.getDietPlanId());
        if (dietPlanOptional.isPresent()) {
            DietPlan dietPlan = dietPlanOptional.get();

            if (dietPlanToUpdate.getDietPlanName() != null && !dietPlanToUpdate.getDietPlanName().equals(dietPlan.getDietPlanName()))
                dietPlan.setDietPlanName(dietPlanToUpdate.getDietPlanName());
            if (dietPlanToUpdate.getClient() != null && !Objects.equals(dietPlanToUpdate.getClient(), dietPlan.getClient()))
                dietPlan.setClient(dietPlan.getClient());
            if (dietPlanToUpdate.getMealList() != null && !dietPlanToUpdate.getMealList().isEmpty() && !Objects.deepEquals(dietPlanToUpdate.getMealList(), dietPlan.getMealList()))
                dietPlan.setMealList(dietPlanToUpdate.getMealList());

            return dietPlanRepository.save(dietPlan);
        } else {
            throw new EntityNotFoundException("DietPlan entity with id " + dietPlanToUpdate.getDietPlanId() + " not found, update failed");
        }
    }
}
