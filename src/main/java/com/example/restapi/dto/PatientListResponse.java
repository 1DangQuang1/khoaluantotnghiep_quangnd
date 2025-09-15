package com.example.restapi.dto;

import java.time.LocalDate;

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
    Long id;
    String fullName;
    String cccd;
    String gender;
    LocalDate birthDate;
    String insuranceNumber;
    String phone;
    String address;
    String departmentName;
    String latestVisitDate;
    Integer visitCount;
    String latestVisitStatus;

}
