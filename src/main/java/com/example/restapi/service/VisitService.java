package com.example.restapi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.example.restapi.dto.VisitRequest;
import com.example.restapi.dto.VisitResponse;
import com.example.restapi.model.Visit;

public interface VisitService {
    VisitResponse createVisit(VisitRequest request);
    List<VisitResponse> listVisits(Visit.VisitStatus status, Long departmentId, LocalDate date);
    VisitResponse getVisit(UUID visitId);
    VisitResponse updateStatus(UUID visitId, Visit.VisitStatus newStatus, Integer lockVersion);
    VisitResponse assignDoctor(UUID visitId, UUID doctorId, UUID roomId);
    VisitResponse getVisitByPatientCccd(String cccd);
    void cancelVisit(UUID visitId, String reason);
}
