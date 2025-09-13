package com.example.restapi.service;


import org.springframework.stereotype.Service;

import com.example.restapi.exceptions.NotFoundException;
import com.example.restapi.model.ClinicalRecord;
import com.example.restapi.model.Visit;
import com.example.restapi.repository.ClinicalRecordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClinicalRecordService {

    private final ClinicalRecordRepository repository;
    private final VisitService visitService;

    public <Optional>ClinicalRecord create(Long visitId, ClinicalRecord record) {
        record.setVisitId(visitId);
        visitService.updateCurrentStep(visitId, Visit.VisitStep.CLINICAL);
        visitService.updateStatus(visitId, Visit.VisitStatus.EXAMINING);
        return repository.save(record);
    }

    public ClinicalRecord getByVisitId(Long visitId) {
        return repository.findByVisitId(visitId
        ).orElseThrow(() -> new NotFoundException("Record not found"));
    }

    public ClinicalRecord update(Long visitId, ClinicalRecord record) {
        ClinicalRecord existing = repository.findByVisitId(visitId)
                .orElseThrow(() -> new NotFoundException("Record not found"));

        record.setId(existing.getId());
        record.setVisitId(existing.getVisitId());
        return repository.save(record);
    }
}
