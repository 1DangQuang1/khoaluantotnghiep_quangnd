package com.example.restapi.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  InvoiceItemResponse {
    private Long visitId;
    private String serviceName;
    private Integer quantity;
    private String unitPrice;
    private BigDecimal eachUnitPrice;
    private BigDecimal totalPrice;
    private BigDecimal insurancePaid;
    private BigDecimal patientPaid;
}
