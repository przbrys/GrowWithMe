package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.BodyInformation;
import com.GrowWithMe.GrowWithMe.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBodyInformationRepository extends JpaRepository<BodyInformation, Integer> {
    List<BodyInformation> findByClient(Client client);
}
