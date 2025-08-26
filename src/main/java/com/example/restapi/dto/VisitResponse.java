package com.example.restapi.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.restapi.model.Visit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VisitResponse {

    private Long id;
    private LocalDate visitDate;
    private Integer shift;
    private Visit.VisitStatus status;
    private Visit.VisitStep currentStep;
    private Integer queueNo;

    private Long patientId;
    private String patientCccd;

    private Long doctorId;
    private Long departmentId;
    private Long roomId;

    private Integer lockVersion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static VisitResponse fromEntity(Visit visit) {
        return VisitResponse.builder()
                .id(visit.getId())
                .visitDate(visit.getVisitDate())
                .shift(visit.getShift())
                .status(visit.getStatus())
                .currentStep(visit.getCurrentStep())
                .queueNo(visit.getQueueNo())
                .patientId(visit.getPatientId())
                .patientCccd(visit.getPatientCccd())
                .doctorId(visit.getDoctorId())
                .departmentId(visit.getDepartmentId())
                .roomId(visit.getRoomId())
                .lockVersion(visit.getLockVersion())
                .createdAt(visit.getCreatedAt())
                .updatedAt(visit.getUpdatedAt())
                .build();
    }
}
