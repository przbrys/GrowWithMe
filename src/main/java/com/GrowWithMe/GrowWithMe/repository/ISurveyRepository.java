package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISurveyRepository extends JpaRepository<Survey, Integer> {
    List<Survey> findByClient(Client client);

    @Query("SELECT s FROM Survey s " +
            "JOIN s.client c " +
            "JOIN c.trainer t " +
            "WHERE t.trainerId=:trainerId")
    List<Survey> getSurveyByTrainerId(@Param("trainerId") Integer trainerId);

}
