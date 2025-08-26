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
@RequestMapping("/api/visits/{visitId}/clinical")
@RequiredArgsConstructor
public class ClinicalRecordController {

    private final ClinicalRecordService service;

    @PostMapping
    public ResponseEntity<ClinicalRecord> create(@PathVariable Long visitId, @RequestBody ClinicalRecord record) {
        return ResponseEntity.ok(service.create(visitId, record));
    }

    @GetMapping
    public ResponseEntity<ClinicalRecord> getByVisit(@PathVariable Long visitId) {
        return ResponseEntity.ok(service.getByVisit(visitId));
    }

    @PutMapping
    public ResponseEntity<ClinicalRecord> update(
            @PathVariable Long visitId,
            @RequestBody ClinicalRecord record) {
        return ResponseEntity.ok(service.update(visitId, record));
    }
}
