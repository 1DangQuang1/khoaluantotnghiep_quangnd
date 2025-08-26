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

import com.example.restapi.model.LabResult;
import com.example.restapi.service.LabResultService;

@RestController
@RequestMapping("/api/visits/{visitId}/labs")
public class LabResultController {

    private final LabResultService labResultService;

    public LabResultController(LabResultService labResultService) {
        this.labResultService = labResultService;
    }

    @PostMapping
    public ResponseEntity<LabResult> createLabResult(
            @PathVariable Long visitId,
            @RequestBody LabResult labResult) {
        LabResult saved = labResultService.createLabResult(visitId, labResult);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<LabResult> getLabResults(@PathVariable Long visitId) {
        return ResponseEntity.ok(labResultService.getLabResults(visitId));
    }

    @PutMapping
    public LabResult updateLabResult(
            @PathVariable Long visitId,
            @RequestBody LabResult update) {
        return labResultService.updateLabResult(visitId, update);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLabResult(
            @RequestBody Long visitId)
            {
            boolean deleted = labResultService.deleteLabResult(visitId);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }
}
