package com.example.restapi.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {
    private Long id;
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
    private InsuranceResponse insurance;
    private String medicalHistory;
    private String message;

    public PatientResponse(Long id, String fullName, LocalDate birthDate, String gender, 
                           String cccd, String bloodType, String phone, String email, 
                           String address, String guardianName, String guardianPhone, 
                           String notes, InsuranceResponse insurance, String medicalHistory) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.cccd = cccd;
        this.bloodType = bloodType;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.notes = notes;
        this.insurance = insurance;
        this.medicalHistory = medicalHistory;
        this.message = "Patient info retrieved successfully";
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InsuranceResponse {
        private boolean hasInsurance;
        private String number;
        private LocalDate expiry;
        private String place;
    }
}
