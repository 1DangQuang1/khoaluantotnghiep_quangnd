package com.example.restapi.controller;
import java.time.LocalDate;
import java.util.List;

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

import com.example.restapi.dto.VisitRequest;
import com.example.restapi.dto.VisitResponse;
import com.example.restapi.model.Visit;
import com.example.restapi.service.VisitService;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping
    public ResponseEntity<VisitResponse> createVisit(@RequestBody VisitRequest request) {
        return ResponseEntity.ok(visitService.createVisit(request));
    }

    @GetMapping
    public ResponseEntity<List<VisitResponse>> listVisits(
            @RequestParam(required = false) Visit.VisitStatus status,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) LocalDate date) {
        return ResponseEntity.ok(visitService.listVisits(status, departmentId, date));
    }

    @GetMapping("/{visitId}")
    public ResponseEntity<VisitResponse> getVisit(@PathVariable Long visitId) {
        return ResponseEntity.ok(visitService.getVisit(visitId));
    }

    @PutMapping("/{visitId}/status")
    public ResponseEntity<VisitResponse> updateStatus(
            @PathVariable Long visitId,
            @RequestParam Visit.VisitStatus status,
            @RequestParam(required = false) Integer lockVersion) {
        return ResponseEntity.ok(visitService.updateStatus(visitId, status, lockVersion));
    }

    @PutMapping("/{visitId}/assign")
    public ResponseEntity<VisitResponse> assignDoctor(
            @PathVariable Long visitId,
            @RequestParam Long doctorId,
            @RequestParam Long roomId) {
        return ResponseEntity.ok(visitService.assignDoctor(visitId, doctorId, roomId));
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<Void> cancelVisit(@RequestParam(required = false) Long visitId,
                                            @RequestParam(required = false) String cccd,
                                            @RequestParam String reason) {
        visitService.cancelVisit(visitId, cccd, reason);
        return ResponseEntity.noContent().build();
    }
    

    @GetMapping("/patient/{cccd}")
    public ResponseEntity<VisitResponse> getVisitByPatientCccd(@PathVariable String cccd) {
        return ResponseEntity.ok(visitService.getVisitByPatientCccd(cccd));
    }
}
