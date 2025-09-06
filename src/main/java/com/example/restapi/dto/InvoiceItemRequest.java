package com.example.restapi.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceItemRequest {
    private String serviceName;
    private Integer quantity;
    private String unitPrice;
    private BigDecimal eachUnitPrice;
    private BigDecimal totalPrice;
    private BigDecimal insurancePaid;
    private BigDecimal patientPaid;
    public Long getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }
}