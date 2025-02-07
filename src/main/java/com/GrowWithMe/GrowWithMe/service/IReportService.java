package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.Report;

import java.util.List;
import java.util.Optional;

public interface IReportService {
    List<Report> getAllReports();

    List<Report> getReportsByTrainerId(Integer trainerId);

    Optional<Report> getReportById(Integer reportId);

    void deleteReportEntity(Integer reportId);

    Report createReportEntity(Report report);

    Report updateReport(Report reportToUpdate);
}
