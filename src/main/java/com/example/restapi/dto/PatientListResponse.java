package com.example.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PatientListResponse {
    Long patientId;
    String patientFullName;
    String patientCccd;
    String patientGender;
    String patientAge;
    String patientBhyt;
    String phone;
    String address;
    String departmentName;
    String latestVisitDate;
    Integer visitCount;
    String latestVisitStatus;

}
