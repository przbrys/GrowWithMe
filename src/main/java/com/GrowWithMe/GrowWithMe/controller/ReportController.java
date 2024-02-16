package com.GrowWithMe.GrowWithMe.controller;
import com.GrowWithMe.GrowWithMe.model.Report;
import com.GrowWithMe.GrowWithMe.service.impl.ReportService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Report")
public class ReportController {
    @Autowired
    private ReportService reportService;
    
    @GetMapping()
    public ResponseEntity<List<Report>> getAllReports(){
        List<Report> reportList=reportService.getAllReports();
        return new ResponseEntity<>(reportList, reportList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Integer id){
        Optional<Report> reportOptional=reportService.getReportById(id);
        return reportOptional.map(report -> new ResponseEntity<>(report, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<Report> createReportEntity(@RequestBody Report report){
        Report createdReport=reportService.createReportEntity(report);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReportEntity(@PathVariable Integer id){
        try {
            reportService.deleteReportEntity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping
    public ResponseEntity<Report> updateReport(@RequestBody Report reportToUpdate){
        try {
            Report updatedReport = reportService.updateReport(reportToUpdate);
            return new ResponseEntity<>(updatedReport, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
