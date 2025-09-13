package com.example.restapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VisitRequest {

    @NotBlank(message = "Patient CCCD is required")
    private String patientCccd;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    @NotNull(message = "Service ID is required")
    private Long serviceId;

    @NotNull(message = "Shift is required")
    private Integer shift;

    private Long doctorId;
    private String reason;     
}
