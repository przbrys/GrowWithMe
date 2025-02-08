package com.GrowWithMe.GrowWithMe.service;


import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Report;
import com.GrowWithMe.GrowWithMe.repository.IReportRepository;
import com.GrowWithMe.GrowWithMe.service.impl.ReportService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    private static final int REPORT_ID = 1;
    private static final int TRAINER_ID = 2;
    private static final Client CLIENT = new Client();
    private static final Report REPORT = generateReport();

    @Mock
    private IReportRepository reportRepository;

    @InjectMocks
    private ReportService tested;

    @Test
    void getAllReports() {
        //given
        List<Report> reports = List.of(REPORT);

        //when
        when(reportRepository.findAll()).thenReturn(reports);
        List<Report> result = tested.getAllReports();

        //then
        assertThat(result).isEqualTo(reports);
    }

    @Test
    void getReportsByTrainerId() {
        //given
        List<Report> reports = List.of(REPORT);

        //when
        when(reportRepository.getReportsByTrainerId(TRAINER_ID)).thenReturn(reports);
        List<Report> result = tested.getReportsByTrainerId(TRAINER_ID);

        //then
        assertThat(result).isEqualTo(reports);
    }


    @Test
    void getReportById__1() {
        //given //when
        when(reportRepository.findById(REPORT_ID)).thenReturn(Optional.of(REPORT));
        Optional<Report> result = tested.getReportById(REPORT_ID);

        //then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(REPORT);
    }

    @Test
    void getReportById__2() {
        //given //when
        when(reportRepository.findById(REPORT_ID)).thenReturn(Optional.empty());
        Optional<Report> result = tested.getReportById(REPORT_ID);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void deleteReportEntity__1() {
        //given //when
        tested.deleteReportEntity(REPORT_ID);

        //then
        verify(reportRepository).deleteById(REPORT_ID);
    }

    @Test
    void deleteReportEntity__2() {
        //given //when //then
        doThrow(EmptyResultDataAccessException.class).when(reportRepository).deleteById(REPORT_ID);

        assertThatThrownBy(() -> tested.deleteReportEntity(REPORT_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Report entity with id " + REPORT_ID + " not found");
    }

    @Test
    void deleteReportEntity__3() {
        //given //when //then
        doThrow(RuntimeException.class).when(reportRepository).deleteById(REPORT_ID);

        assertThatThrownBy(() -> tested.deleteReportEntity(REPORT_ID))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deleting Report");
    }

    @Test
    void createReportEntity__1() {
        //given //when
        Report result = tested.createReportEntity(REPORT);

        //then
        verify(reportRepository).save(REPORT);
        assertThat(result).isEqualTo(REPORT);
    }

    @Test
    void createReportEntity__2() {
        //given //when //then
        doThrow(RuntimeException.class).when(reportRepository).save(REPORT);

        assertThatThrownBy(() -> tested.createReportEntity(REPORT))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error in creating new Report.");
    }

    @Test
    void updateReport__1() {
        //given
        Report reportToUpdate = new Report();
        reportToUpdate.setReportId(REPORT_ID);
        reportToUpdate.setReportClientMessage("Updated Client Message");
        reportToUpdate.setReportTrainerMessage("Updated Trainer Message");
        reportToUpdate.setReportDate("2024-08-02");
        reportToUpdate.setClient(CLIENT);


        Report expectedReport = new Report();
        expectedReport.setReportId(REPORT_ID);
        expectedReport.setReportClientMessage("Updated Client Message");
        expectedReport.setReportTrainerMessage("Updated Trainer Message");
        expectedReport.setReportDate("2024-08-02");
        expectedReport.setClient(CLIENT);

        //when
        when(reportRepository.findById(REPORT_ID)).thenReturn(Optional.of(REPORT));
        when(reportRepository.save(any(Report.class))).thenReturn(expectedReport);
        Report result = tested.updateReport(reportToUpdate);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getReportClientMessage()).isEqualTo("Updated Client Message");
        assertThat(result.getReportTrainerMessage()).isEqualTo("Updated Trainer Message");
        assertThat(result.getReportDate()).isEqualTo("2024-08-02");
        assertThat(result.getClient()).isEqualTo(CLIENT);
        verify(reportRepository).save(argThat(r -> r.getReportClientMessage().equals("Updated Client Message")
                && r.getReportTrainerMessage().equals("Updated Trainer Message")
                && r.getReportDate().equals("2024-08-02") && r.getClient().equals(CLIENT)));
    }

    @Test
    void updateReport__2() {
        //given
        Report reportToUpdate = new Report();
        reportToUpdate.setReportId(REPORT_ID);

        //when
        when(reportRepository.findById(REPORT_ID)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> tested.updateReport(reportToUpdate))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Report entity with id " + REPORT_ID + " not found, update failed");
    }
    private static Report generateReport() {
        Report report = new Report();
        report.setReportId(REPORT_ID);
        report.setReportClientMessage("Test Client Message");
        report.setReportTrainerMessage("Test Trainer Message");
        report.setReportDate("2024-08-01");
        report.setClient(CLIENT);
        return report;
    }

}