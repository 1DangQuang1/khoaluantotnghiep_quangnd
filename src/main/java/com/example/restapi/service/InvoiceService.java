package com.example.restapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.example.restapi.dto.InvoiceResponse;
import com.example.restapi.dto.InvoiceItemResponse;
import com.example.restapi.model.Invoice;
import com.example.restapi.model.InvoiceItem;
import com.example.restapi.model.Visit;
import com.example.restapi.repository.InvoiceItemResponseRepository;
import com.example.restapi.repository.InvoiceRepository;
import com.example.restapi.repository.VisitRepository;

import com.example.restapi.exceptions.NotFoundException;

import jakarta.transaction.Transactional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemResponseRepository invoiceItemResponseRepository;
    private final VisitRepository visitRepository;

    public InvoiceService(InvoiceRepository invoiceRepository,
                          InvoiceItemResponseRepository invoiceItemResponseRepository,
                          VisitRepository visitRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemResponseRepository = invoiceItemResponseRepository;
        this.visitRepository = visitRepository;
    }

    @Transactional
    public InvoiceResponse createInvoiceFromVisit(Long visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("Visit not found"));

        // Query items from native query
        List<InvoiceItemResponse> items = invoiceItemResponseRepository.findByVisitId(visitId);

        // Compute total
        BigDecimal totalAmount = items.stream()
                .map(i -> i.getTotalPrice() != null ? i.getTotalPrice() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create Invoice entity in DB
        Invoice invoice = new Invoice();
        invoice.setVisitId(visitId);
        invoice.setPatientCccd(visit.getPatientCccd());
        invoice.setPatientName(visit.getPatientFullName());
        invoice.setTotalAmount(totalAmount);

        Invoice saved = invoiceRepository.save(invoice);

        // Return InvoiceResponse DTO
        return InvoiceResponse.builder()
                .id(saved.getId())
                .visitId(saved.getVisitId())
                .patientCccd(saved.getPatientCccd())
                .patientName(saved.getPatientName())
                .totalAmount(saved.getTotalAmount())
                .createdAt(saved.getCreatedAt())
                .items(items)
                .build();
    }
}



