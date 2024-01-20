package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.BodyInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBodyInformationRepository extends JpaRepository<BodyInformation, Integer> {
}
