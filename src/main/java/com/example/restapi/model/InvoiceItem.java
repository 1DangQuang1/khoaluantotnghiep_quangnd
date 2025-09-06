package com.example.restapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "emr_invoice_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Loại dịch vụ (Khám bệnh, Xét nghiệm, Thuốc, Vật tư, ...)
    @Column(name = "visit_id", nullable = false, length = 100)
    private Long visitId;

    // Tên dịch vụ hoặc thuốc
    @Column(name = "service_name", nullable = false, length = 255)
    private String serviceName;

    // Số lượng
    @Column(nullable = false)
    private Integer quantity;

    // Đơn giá (VNĐ)
    @Column(name = "unit_price", nullable = false, precision = 15, scale = 2)
    private String unitPrice;

    @Column(name = "each_unit_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal eachUnitPrice;

    // Thành tiền = Số lượng * Đơn giá
    @Column(name = "total_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalPrice;

    // BHYT chi trả
    @Column(name = "insurance_paid", precision = 15, scale = 2)
    private BigDecimal insurancePaid;

    // Bệnh nhân tự trả
    @Column(name = "patient_paid", precision = 15, scale = 2)
    private BigDecimal patientPaid;

    // FK to Invoice
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;
}
