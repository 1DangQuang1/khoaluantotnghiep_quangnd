package com.example.restapi.controller;


import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi.model.LabRecord;
import com.example.restapi.model.Visit;
import com.example.restapi.service.LabRecordService;
import com.example.restapi.service.VisitService;

@RestController
@RequestMapping("/api/visits/{visitId}/labs")
public class LabRecordController {

    private final LabRecordService labResultService;
    private final VisitService visitService;

    
    public LabRecordController(LabRecordService labResultService, VisitService visitService) {
            this.labResultService = labResultService;
            this.visitService = visitService;
    }

    @PostMapping
    public ResponseEntity<LabRecord> createLabResult(
            @PathVariable Long visitId,
            @RequestBody LabRecord labResult) {
        LabRecord saved = labResultService.createLabResult(visitId, labResult);
        visitService.updateCurrentStep(visitId, Visit.VisitStep.PARACLINICAL);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<LabRecord> getLabResults(@PathVariable Long visitId) {
        return ResponseEntity.ok(labResultService.getLabResults(visitId));
    }

    @PutMapping
    public LabRecord updateLabResult(
            @PathVariable Long visitId,
            @RequestBody LabRecord update) {
        return labResultService.updateLabResult(visitId, update);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLabResult(
        @PathVariable Long visitId)
        {
        boolean deleted = labResultService.deleteLabResult(visitId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
