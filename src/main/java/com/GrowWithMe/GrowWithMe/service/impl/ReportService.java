package com.GrowWithMe.GrowWithMe.service.impl;
import com.GrowWithMe.GrowWithMe.model.Report;
import com.GrowWithMe.GrowWithMe.repository.IReportRepository;
import com.GrowWithMe.GrowWithMe.service.IReportService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService implements IReportService{
    @Autowired
    IReportRepository reportRepository;

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public Optional<Report> getReportById(Integer reportId) {
        return reportRepository.findById(reportId);
    }

    @Override
    public void deleteReportEntity(Integer reportId) {
        try {
            reportRepository.deleteById(reportId);
        }catch (EmptyResultDataAccessException e) {
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
        }catch (Exception e){
            throw new RuntimeException("Error in creating new Report.",e);
        }
    }

    @Override
    public Report updateReport(Report reportToUpdate) {
        Optional<Report> reportOptional =reportRepository.findById(reportToUpdate.getReportId());
        if (reportOptional.isPresent()) {
            reportRepository.save(reportToUpdate);
            return reportRepository.save(reportToUpdate);
        } else {
            throw new EntityNotFoundException("Report entity with id " + reportToUpdate.getReportId() + " not found, update failed");
        }
    }
}
