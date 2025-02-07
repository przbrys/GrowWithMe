package com.GrowWithMe.GrowWithMe.service.impl;

import com.GrowWithMe.GrowWithMe.model.Report;
import com.GrowWithMe.GrowWithMe.repository.IReportRepository;
import com.GrowWithMe.GrowWithMe.service.IReportService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReportService implements IReportService {
    @Autowired
    private IReportRepository reportRepository;


    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public List<Report> getReportsByTrainerId(Integer trainerId) {
        return reportRepository.getReportsByTrainerId(trainerId);
    }

    @Override
    public Optional<Report> getReportById(Integer reportId) {
        return reportRepository.findById(reportId);
    }

    @Override
    public void deleteReportEntity(Integer reportId) {
        try {
            reportRepository.deleteById(reportId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Report entity with id " + reportId + " not found", e);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Report", e);
        }
    }

    @Override
    public Report createReportEntity(Report report) {
        try {
            reportRepository.save(report);
            return report;
        } catch (Exception e) {
            throw new RuntimeException("Error in creating new Report.", e);
        }
    }

    @Override
    public Report updateReport(Report reportToUpdate) {
        Optional<Report> reportOptional = reportRepository.findById(reportToUpdate.getReportId());
        if (reportOptional.isPresent()) {
            Report report = reportOptional.get();

            if (reportToUpdate.getReportDate() != null && !reportToUpdate.getReportDate().equals(report.getReportDate()))
                report.setReportDate(reportToUpdate.getReportDate());
            if (reportToUpdate.getReportClientMessage() != null && !reportToUpdate.getReportClientMessage().equals(report.getReportClientMessage()))
                report.setReportClientMessage(reportToUpdate.getReportClientMessage());
            if (reportToUpdate.getReportTrainerMessage() != null && !reportToUpdate.getReportTrainerMessage().equals(report.getReportTrainerMessage()))
                report.setReportTrainerMessage(reportToUpdate.getReportTrainerMessage());
            if (reportToUpdate.getClient() != null && !Objects.equals(reportToUpdate.getClient(), report.getClient()))
                report.setClient(reportToUpdate.getClient());

            return reportRepository.save(report);
        } else {
            throw new EntityNotFoundException("Report entity with id " + reportToUpdate.getReportId() + " not found, update failed");
        }
    }
}
