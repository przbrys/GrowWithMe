package com.GrowWithMe.GrowWithMe.service.impl;

import com.GrowWithMe.GrowWithMe.model.BodyInformation;
import com.GrowWithMe.GrowWithMe.repository.IBodyInformationRepository;
import com.GrowWithMe.GrowWithMe.service.IBodyInformationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BodyInformationService implements IBodyInformationService {

    @Autowired
    private IBodyInformationRepository bodyInformationRepository;

    @Override
    public List<BodyInformation> getAllBodyInformation() {
        return bodyInformationRepository.findAll();
    }

    @Override
    public Optional<BodyInformation> getBodyInformationById(Integer bodyInformationId) {
        return bodyInformationRepository.findById(bodyInformationId);
    }

    @Override
    public void deleteBodyInformationEntity(Integer bodyInformationId) {
        try {
            bodyInformationRepository.deleteById(bodyInformationId);
        }catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("BodyInformation entity with id " + bodyInformationId + " not found", e);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting BodyInformation", e);
        }
    }

    @Override
    public BodyInformation createBodyInformationEntity(BodyInformation bodyInformation) {
        try {
            bodyInformationRepository.save(bodyInformation);
            return bodyInformation;
        }catch (Exception e){
            throw new RuntimeException("Error in creating new BodyInformation.",e);
        }
    }
    @Override
    public BodyInformation updateBodyTrainerAdditionalInformation(BodyInformation bodyTrainerAdditionalInformation) {
        Optional<BodyInformation> bodyInformationOptional = bodyInformationRepository.findById(bodyTrainerAdditionalInformation.getBodyInformationId());
        if (bodyInformationOptional.isPresent()) {
            BodyInformation bodyInformation = bodyInformationOptional.get();
            bodyInformation.setBodyTrainerAdditionalInformation(bodyTrainerAdditionalInformation.getBodyTrainerAdditionalInformation());
            return bodyInformationRepository.save(bodyInformation);
        } else {
            throw new EntityNotFoundException("BodyInformation entity with id " + bodyTrainerAdditionalInformation.getBodyInformationId() + " not found");
        }
    }
}