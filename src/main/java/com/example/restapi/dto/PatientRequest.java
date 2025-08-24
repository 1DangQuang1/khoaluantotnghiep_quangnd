package com.example.restapi.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequest {
    private String fullName;
    private LocalDate birthDate;
    private String gender;
    private String cccd;
    private String bloodType;
    private String phone;
    private String email;
    private String address;
    private String guardianName;
    private String guardianPhone;
    private String notes;
    private InsuranceRequest insurance;
    private String medicalHistory;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InsuranceRequest {
        private boolean hasInsurance;
        private String number;
        private LocalDate expiry;
        private String place;
    }
}
