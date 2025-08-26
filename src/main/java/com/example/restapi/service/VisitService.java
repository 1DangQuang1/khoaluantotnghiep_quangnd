package com.example.restapi.service;

import java.time.LocalDate;
import java.util.List;

import com.example.restapi.dto.VisitRequest;
import com.example.restapi.dto.VisitResponse;
import com.example.restapi.model.Visit;

public interface VisitService {
    VisitResponse createVisit(VisitRequest request);
    List<VisitResponse> listVisits(Visit.VisitStatus status, Long departmentId, LocalDate date);
    VisitResponse getVisit(Long visitId);
    VisitResponse updateStatus(Long visitId, Visit.VisitStatus newStatus, Integer lockVersion);
    VisitResponse assignDoctor(Long visitId, Long doctorId, Long roomId);
    VisitResponse getVisitByPatientCccd(String cccd);
    void cancelVisit(Long visitId, String cccd, String reason);
}
