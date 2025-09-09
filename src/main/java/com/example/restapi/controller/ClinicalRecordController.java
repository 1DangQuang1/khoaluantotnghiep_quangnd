package com.example.restapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi.model.ClinicalRecord;
import com.example.restapi.service.ClinicalRecordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class ClinicalRecordController {

    private final ClinicalRecordService service;

    @PostMapping("/{visitId}/clinical")
    public ResponseEntity<ClinicalRecord> create(@PathVariable Long visitId, @RequestBody ClinicalRecord record) {
        ClinicalRecord saved = service.create(visitId, record);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{visitId}/clinical")
    public ResponseEntity<ClinicalRecord> get(@PathVariable Long visitId) {
        return ResponseEntity.ok(service.getByVisitId(visitId));
    }

    @PutMapping("/{visitId}/clinical")
    public ResponseEntity<ClinicalRecord> update(
            @PathVariable Long visitId,
            @RequestBody ClinicalRecord record) {
        return ResponseEntity.ok(service.update(visitId, record));
    }


}
