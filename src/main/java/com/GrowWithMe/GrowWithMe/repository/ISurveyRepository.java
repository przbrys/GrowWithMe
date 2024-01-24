package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISurveyRepository extends JpaRepository<Survey, Integer> {
    List<Survey> findByClient(Client client);
}
