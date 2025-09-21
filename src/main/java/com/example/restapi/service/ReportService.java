package com.example.restapi.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restapi.dto.PatientReport;
import com.example.restapi.dto.RevReport;
import com.example.restapi.repository.InvoiceRepository;
import com.example.restapi.repository.PatientReportRepository;
import com.example.restapi.repository.VisitRepository;


@Service
public class ReportService {
    private final PatientReportRepository PatientReportRepository;
    private final InvoiceRepository invoiceRepository;
    private final VisitRepository visitRepository;

    public ReportService(PatientReportRepository PatientReportRepository, InvoiceRepository invoiceRepository, VisitRepository visitRepository) {
        this.PatientReportRepository = PatientReportRepository;
        this.invoiceRepository = invoiceRepository;
        this.visitRepository = visitRepository;
    }
    
    public PatientReport getInpatientReport() {
        long current = PatientReportRepository.countInpatients();

        LocalDate yesterday = LocalDate.now().minusDays(1);
        long yesterdayCount = PatientReportRepository.countYesterdayInpatients(yesterday);


        return new PatientReport(current, yesterdayCount);
    }


    public PatientReport getOutpatientReport() {
        // Tổng số hiện tại
        long current = PatientReportRepository.countOutpatients();
        LocalDate yesterday = LocalDate.now().minusDays(1);

        long yesterdayCount = PatientReportRepository.countYesterdayOutpatients(yesterday);

        // Tính chênh lệch

        return new PatientReport(current, yesterdayCount);
    }

    public RevReport getTotalRevenue() {
        // Tổng số hiện tại
        Double current = invoiceRepository.getTotalRevenue();

        Double yesterdayCount = invoiceRepository.getTodayRevenue() != null ? invoiceRepository.getTodayRevenue(): 0;

        // Tính chênh lệch
        Double diff = (yesterdayCount / current) * 100;

        return new RevReport(current, diff);
    }

    public <T> List<T> getVisitCountByDepartment() {
        return (List<T>) visitRepository.countByDepartment();
    }
    
}
