package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.DietPlan;
import com.GrowWithMe.GrowWithMe.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByClient(Client client);

    @Query("SELECT r FROM Report r " +
            "JOIN r.client c " +
            "JOIN c.trainer t " +
            "WHERE t.trainerId = :trainerId")
    List<Report> getReportsByTrainerId(@Param("trainerId") Integer trainerId);
}
