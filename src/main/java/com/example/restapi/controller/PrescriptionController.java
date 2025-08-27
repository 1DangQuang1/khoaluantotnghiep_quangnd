package com.example.restapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi.dto.PrescriptionRequest;
import com.example.restapi.dto.PrescriptionResponse;
import com.example.restapi.service.PrescriptionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/visits/{visitId}/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    /**
     * Lấy đơn thuốc theo visitId
     */
    @GetMapping
    public ResponseEntity<PrescriptionResponse> getPrescriptionByVisit(
            @PathVariable Long visitId) {
        PrescriptionResponse response = prescriptionService.getPrescriptionByVisit(visitId);
        return ResponseEntity.ok(response);
    }

    /**
     * Tạo đơn thuốc mới cho visit
     */
    @PostMapping
    public ResponseEntity<PrescriptionResponse> createPrescription(
            @PathVariable Long visitId,
            @RequestBody PrescriptionRequest request) {
        PrescriptionResponse response = prescriptionService.createPrescription(visitId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{prescriptionId}")
    public ResponseEntity<PrescriptionResponse> updatePrescription(
            @PathVariable Long prescriptionId,
            @RequestBody PrescriptionRequest request) {
        PrescriptionResponse response = prescriptionService.updatePrescription(prescriptionId, request);
        return ResponseEntity.ok(response);
    }

}
