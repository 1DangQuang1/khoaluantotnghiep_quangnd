package com.example.restapi.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi.dto.VisitResponse;
import com.example.restapi.model.ClinicalRecord;
import com.example.restapi.model.Visit;
import com.example.restapi.service.ClinicalRecordService;
import com.example.restapi.service.VisitService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class ClinicalRecordController {

    private final ClinicalRecordService service;
    private final VisitService visitService;

    @PostMapping("/{visitId}/clinical")
    public ResponseEntity<ClinicalRecord> create(@PathVariable Long visitId, @RequestBody ClinicalRecord record) {
        ClinicalRecord saved = service.create(visitId, record);
        visitService.updateCurrentStep(visitId, Visit.VisitStep.CLINICAL);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{visitId}/clinical")
    public ResponseEntity<ClinicalRecord> getByVisit(@PathVariable Long visitId) {
        return ResponseEntity.ok(service.getByVisit(visitId));
    }

    @PutMapping("/{visitId}/clinical")
    public ResponseEntity<ClinicalRecord> update(
            @PathVariable Long visitId,
            @RequestBody ClinicalRecord record) {
        return ResponseEntity.ok(service.update(visitId, record));
    }

    @PutMapping("/clinical/{patientCccd}")
    public ResponseEntity<ClinicalRecord> updateByCccd(
            @PathVariable String patientCccd,
            @RequestBody ClinicalRecord record) {
        VisitResponse visit = visitService.getVisitByPatientCccd(patientCccd);
        Long visitId = visit.getId();
        return ResponseEntity.ok(service.update(visitId, record));
    }

}
