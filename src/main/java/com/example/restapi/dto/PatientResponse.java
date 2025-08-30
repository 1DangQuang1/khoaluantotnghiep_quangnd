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
    private String insuranceNumber;
    private String medicalHistory;
    private String message;

    public PatientResponse(Long id, String fullName, LocalDate birthDate, String gender, 
                           String cccd, String bloodType, String phone, String email, 
                           String address, String guardianName, String guardianPhone, 
                           String notes, String insuranceNumber, String medicalHistory) {
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
        this.insuranceNumber = insuranceNumber;
        this.medicalHistory = medicalHistory;
        this.message = "Lấy dữ liệu bệnh nhân thành công";
    }

}
