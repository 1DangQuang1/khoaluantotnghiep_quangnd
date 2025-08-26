package com.example.restapi.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionResponse {

    private Long id;
    private Long visitId;
    private Long doctorId;
    private String notes;
    private LocalDateTime createdAt;
    private List<PrescriptionItemResponseDto> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrescriptionItemResponseDto {
        private Long id;
        private String drugCode;

        private Integer dosePerTime;
        private Integer timesPerDay;
        private Integer days;
        private Integer totalQuantity;

        private String drugName;
        private String activeSubstance;
        private String dosageForm;
        private String strength;
        private String unit;
        private String warnings;
        private String manufacturer;
        private Double price;
        private String instructions;

    }
}
