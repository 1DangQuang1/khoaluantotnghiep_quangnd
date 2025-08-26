package com.example.restapi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionRequest {

    private Long visitId;
    private Long doctorId;
    private String notes;
    private List<PrescriptionItemRequestDto> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrescriptionItemRequestDto {
        private String drugCode;
        private Integer dosePerTime;
        private Integer timesPerDay;
        private Integer days;
    }
}
