package com.example.restapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceRequest {
    private Long id;
    private Long visitId;
    private String patientCccd;
    private String patientName;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<InvoiceItemRequest> items;
}