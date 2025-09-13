package com.example.restapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restapi.dto.VisitRequest;
import com.example.restapi.dto.VisitResponse;
import com.example.restapi.model.Visit;

@Service
public interface VisitService {
    VisitResponse createVisit(VisitRequest request);
    List<VisitResponse> listVisits(Visit.VisitStatus status, Long departmentId);
    VisitResponse getVisit(Long visitId);
    VisitResponse updateStatus(Long visitId, Visit.VisitStatus newStatus);
    VisitResponse updateCurrentStep(Long visitId, Visit.VisitStep newStep);
    VisitResponse getVisitByPatientCccd(String cccd);
    void cancelVisit(String cccd, String reason);
    VisitResponse doneExamine(Long visitId);
}
