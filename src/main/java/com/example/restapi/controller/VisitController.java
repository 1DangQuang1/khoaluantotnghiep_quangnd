package com.example.restapi.controller;

import com.example.restapi.dto.VisitRequest;
import com.example.restapi.dto.VisitResponse;
import com.example.restapi.model.Visit;
import com.example.restapi.service.VisitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<VisitResponse> getVisit(@PathVariable UUID visitId) {
        return ResponseEntity.ok(visitService.getVisit(visitId));
    }

    @PutMapping("/{visitId}/status")
    public ResponseEntity<VisitResponse> updateStatus(
            @PathVariable UUID visitId,
            @RequestParam Visit.VisitStatus status,
            @RequestParam(required = false) Integer lockVersion) {
        return ResponseEntity.ok(visitService.updateStatus(visitId, status, lockVersion));
    }

    @PutMapping("/{visitId}/assign")
    public ResponseEntity<VisitResponse> assignDoctor(
            @PathVariable UUID visitId,
            @RequestParam UUID doctorId,
            @RequestParam UUID roomId) {
        return ResponseEntity.ok(visitService.assignDoctor(visitId, doctorId, roomId));
    }

    @DeleteMapping("/{visitId}")
    public ResponseEntity<Void> cancelVisit(@PathVariable UUID visitId, @RequestParam String reason) {
        visitService.cancelVisit(visitId, reason);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{cccd}")
    public ResponseEntity<VisitResponse> getVisitByPatientCccd(@PathVariable String cccd) {
        return ResponseEntity.ok(visitService.getVisitByPatientCccd(cccd));
    }
}
