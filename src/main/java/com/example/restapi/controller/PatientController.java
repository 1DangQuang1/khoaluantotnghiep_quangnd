package com.example.restapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi.dto.PatientRequest;
import com.example.restapi.dto.PatientResponse;
import com.example.restapi.service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    // Create patient
    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@RequestBody PatientRequest request) {
        return ResponseEntity.ok(patientService.createPatient(request));
    }

    // Get patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    // Get patient by CCCD
    @GetMapping("/cccd/{cccd}")
    public ResponseEntity<PatientResponse> getPatientByCccd(@PathVariable String cccd) {
        return ResponseEntity.ok(patientService.getPatientByCccd(cccd));
    }

    // Get all patients
    @GetMapping("/list/{page}/{size}")
    public ResponseEntity<List<PatientResponse>> getListPatients(
        @PathVariable int page,
        @PathVariable int size
    ) {
        return ResponseEntity.ok(patientService.getListPatients(page, size).getContent());
    }

    // ===== UPDATE =====
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatientById(
            @PathVariable Long id,
            @RequestBody PatientRequest request) {
        return ResponseEntity.ok(patientService.updatePatientById(id, request));
    }

    @PutMapping("/cccd/{cccd}")
    public ResponseEntity<PatientResponse> updatePatientByCccd(
            @PathVariable String cccd,
            @RequestBody PatientRequest request) {
        return ResponseEntity.ok(patientService.updatePatientByCccd(cccd, request));
    }

    // ===== DELETE =====
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientById(@PathVariable Long id) {
        patientService.deletePatientById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cccd/{cccd}")
    public ResponseEntity<Void> deletePatientByCccd(@PathVariable String cccd) {
        patientService.deletePatientByCccd(cccd);
        return ResponseEntity.noContent().build();
    }

}
