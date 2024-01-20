package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.BodyInformation;

import java.util.List;
import java.util.Optional;

public interface IBodyInformationService {
    List<BodyInformation> getAllBodyInformation();
    Optional<BodyInformation> getBodyInformationById(Integer bodyInformationId);
    void deleteBodyInformation(Integer bodyInformationId);
    BodyInformation createBodyInformationEntity(BodyInformation bodyInformation);
    BodyInformation updateBodyTrainerAdditionalInformation(Integer bodyInformationId, String trainerAdditionalInformation);
}
