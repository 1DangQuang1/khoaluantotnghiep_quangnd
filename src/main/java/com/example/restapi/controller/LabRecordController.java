package com.example.restapi.controller;


import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi.dto.VisitResponse;
import com.example.restapi.model.LabRecord;
import com.example.restapi.model.Visit;
import com.example.restapi.service.LabRecordService;
import com.example.restapi.service.VisitService;

@RestController
@RequestMapping("/api/visits")
public class LabRecordController {

    private final LabRecordService labResultService;
    private final VisitService visitService;

    
    public LabRecordController(LabRecordService labResultService, VisitService visitService) {
            this.labResultService = labResultService;
            this.visitService = visitService;
    }

    @PostMapping("/{visitId}/lab-results")
    public ResponseEntity<LabRecord> createLabResult(
            @PathVariable Long visitId,
            @RequestBody LabRecord labResult) {
        LabRecord saved = labResultService.createLabResult(visitId, labResult);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{visitId}/lab-results")
    public ResponseEntity<List<LabRecord>> getLabResults(@PathVariable Long visitId) {
        return ResponseEntity.ok(labResultService.getLabResults(visitId));
    }


    @PutMapping("/{visitId}/lab-results")
    public LabRecord updateLatestLabRecord(
            @PathVariable Long visitId,
            @RequestBody LabRecord update) {
        return labResultService.updateLabRecordByVisitId(visitId, update);
    }

    @PutMapping("/lab-results/{cccd}")
    public LabRecord updateLatestLabRecord(
            @PathVariable String cccd,
            @RequestParam String type,
            @RequestBody LabRecord update) {
        VisitResponse visit = visitService.getVisitByPatientCccd(cccd); 
        Long visitId = visit.getId();       
        return labResultService.updateLabRecordByCccd(visitId, type, update);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Void> deleteLabResult(
        @PathVariable Long visitId,
        @PathVariable Long Id)
        {
        boolean deleted = labResultService.deleteLabResult(Id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
