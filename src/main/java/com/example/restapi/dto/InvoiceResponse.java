package com.example.restapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceResponse {
    private Long id;
    private Long visitId;
    private String patientCccd;
    private String patientName;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private List<InvoiceItemResponse> items;
    

}


