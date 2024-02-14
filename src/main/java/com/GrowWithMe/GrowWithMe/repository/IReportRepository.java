package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByClient(Client client);
}
