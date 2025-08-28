package com.example.restapi.service;


import org.springframework.stereotype.Service;

import com.example.restapi.exceptions.DuplicateException;
import com.example.restapi.exceptions.VisitNotFoundException;
import com.example.restapi.model.ClinicalRecord;
import com.example.restapi.repository.ClinicalRecordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClinicalRecordService {

    private final ClinicalRecordRepository repository;

    public <Optional>ClinicalRecord create(Long visitId, ClinicalRecord record) {
        if (repository.findByVisitId(visitId).isPresent()) {
            throw new DuplicateException("Record already exists for visitId: " + visitId);
        }
        record.setVisitId(visitId);
        return repository.save(record);
    }

    public ClinicalRecord getByVisit(Long visitId) {
        return repository.findByVisitId(visitId
        ).orElseThrow(() -> new VisitNotFoundException("Record not found"));
    }

    public ClinicalRecord update(Long visitId, ClinicalRecord record) {
        ClinicalRecord existing = repository.findByVisitId(visitId)
                .orElseThrow(() -> new VisitNotFoundException("Record not found"));

        record.setId(existing.getId());
        record.setVisitId(existing.getVisitId());
        return repository.save(record);
    }
}
