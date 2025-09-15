package com.example.restapi.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.restapi.dto.InvoiceItemResponse;
import com.example.restapi.dto.InvoiceRequest;
import com.example.restapi.dto.InvoiceResponse;
import com.example.restapi.exceptions.NotFoundException;
import com.example.restapi.model.Invoice;
import com.example.restapi.model.InvoiceItem;
import com.example.restapi.model.Visit;
import com.example.restapi.repository.InvoiceItemResponseRepository;
import com.example.restapi.repository.InvoiceRepository;
import com.example.restapi.repository.VisitRepository;


import jakarta.transaction.Transactional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemResponseRepository invoiceItemResponseRepository;
    private final VisitRepository visitRepository;
    private final VisitService visitService;

    public InvoiceService(InvoiceRepository invoiceRepository,
                          InvoiceItemResponseRepository invoiceItemResponseRepository,
                          VisitRepository visitRepository,
                          VisitService visitService) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemResponseRepository = invoiceItemResponseRepository;
        this.visitRepository = visitRepository;
        this.visitService = visitService;
    }

    @Transactional
    public InvoiceResponse createInvoiceFromVisit(Long visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("Visit not found"));

        List<InvoiceItemResponse> items = invoiceItemResponseRepository.findByVisitId(visitId);

        BigDecimal totalAmount = items.stream()
                .map(i -> i.getPatientPaid() != null ? i.getPatientPaid() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Invoice invoice = new Invoice();
        invoice.setId(invoiceRepository.getMaxId() + 1);
        invoice.setVisitId(visitId);
        invoice.setPatientCccd(visit.getPatientCccd());
        invoice.setPatientName(visit.getPatientFullName());
        invoice.setTotalAmount(totalAmount);

        // Invoice invoice = invoiceRepository.save(invoice);

        // Return InvoiceResponse DTO
        return InvoiceResponse.builder()
                .id(invoice.getId())
                .visitId(invoice.getVisitId())
                .patientCccd(invoice.getPatientCccd())
                .patientName(invoice.getPatientName())
                .totalAmount(invoice.getTotalAmount())
                .createdAt(invoice.getCreatedAt())
                .items(items)
                .build();
    }

    @Transactional
    public InvoiceResponse saveInvoice(Long visitId, InvoiceRequest request) {
        // entityManager.clear(); 
        Invoice invoice = new Invoice();
        // invoice.setId(request.getId()); // use ID from body
        invoice.setVisitId(request.getVisitId());
        invoice.setPatientCccd(request.getPatientCccd());
        invoice.setPatientName(request.getPatientName());
        invoice.setTotalAmount(request.getTotalAmount());
        invoice.setCreatedAt(request.getCreatedAt() != null ? request.getCreatedAt() : LocalDateTime.now());
    
        List<InvoiceItem> items = request.getItems().stream()
                .map(i -> InvoiceItem.builder()
                        // no ID here -> let DB generate
                        .visitId(request.getVisitId())
                        .serviceName(i.getServiceName())
                        .quantity(i.getQuantity())
                        .unitPrice(i.getUnitPrice())
                        .eachUnitPrice(i.getEachUnitPrice())
                        .totalPrice(i.getTotalPrice())
                        .insurancePaid(i.getInsurancePaid())
                        .patientPaid(i.getPatientPaid())
                        .invoice(invoice) // link back
                        .build())
                .toList();
    
        invoice.setItems(items);
    
        visitService.updateStatus(visitId, Visit.VisitStatus.DONE);
        visitService.updateCurrentStep(visitId, Visit.VisitStep.BILLING);
        Invoice saved = invoiceRepository.saveAndFlush(invoice);
    
        // map to response
        List<InvoiceItemResponse> itemResponses = saved.getItems().stream()
                .map(it -> new InvoiceItemResponse(
                        it.getServiceName(),
                        it.getQuantity(),
                        it.getUnitPrice(),
                        it.getEachUnitPrice(),
                        it.getTotalPrice(),
                        it.getInsurancePaid(),
                        it.getPatientPaid()
                ))
                .toList();
    
        return InvoiceResponse.builder()
                .id(saved.getId())
                .visitId(saved.getVisitId())
                .patientCccd(saved.getPatientCccd())
                .patientName(saved.getPatientName())
                .totalAmount(saved.getTotalAmount())
                .createdAt(saved.getCreatedAt())
                .items(itemResponses)
                .build();
    }


    public InvoiceResponse getInvoiceByVisitId(Long visitId) {
        Invoice invoice = invoiceRepository.findByVisitId(visitId)
                .orElseThrow(() -> new NotFoundException("Invoice not found for visitId: " + visitId));

        // map items -> response
        List<InvoiceItemResponse> itemResponses = invoice.getItems().stream()
                .map(it -> InvoiceItemResponse.builder()
                        .serviceName(it.getServiceName())
                        .quantity(it.getQuantity())
                        .unitPrice(it.getUnitPrice())
                        .eachUnitPrice(it.getEachUnitPrice())
                        .totalPrice(it.getTotalPrice())
                        .insurancePaid(it.getInsurancePaid())
                        .patientPaid(it.getPatientPaid())
                        .build())
                .toList();

        return InvoiceResponse.builder()
                .id(invoice.getId())
                .visitId(invoice.getVisitId())
                .patientCccd(invoice.getPatientCccd())
                .patientName(invoice.getPatientName())
                .totalAmount(invoice.getTotalAmount())
                .createdAt(invoice.getCreatedAt())
                .items(itemResponses)
                .build();
    }
    

    
}



