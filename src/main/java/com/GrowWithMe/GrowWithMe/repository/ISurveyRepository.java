package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISurveyRepository extends JpaRepository<Survey, Integer> {
}
