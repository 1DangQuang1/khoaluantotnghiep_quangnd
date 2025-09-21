package com.example.restapi.controller;

import com.example.restapi.dto.PatientReport;
import com.example.restapi.dto.RevReport;
import com.example.restapi.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // Inpatient report
    @GetMapping("/inpatients")
    public PatientReport getInpatientReport() {
        return reportService.getInpatientReport();
    }

    // Outpatient report
    @GetMapping("/outpatients")
    public PatientReport getOutpatientReport() {
        return reportService.getOutpatientReport();
    }

    // Revenue report
    @GetMapping("/revenue")
    public RevReport getTotalRevenue() {
        return reportService.getTotalRevenue();
    }

    @GetMapping("/departments")
    public List<?> getVisitCountByDepartment() {
        return reportService.getVisitCountByDepartment();
    }
}