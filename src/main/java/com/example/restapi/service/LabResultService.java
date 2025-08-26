package com.example.restapi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.restapi.exceptions.RecordNotFoundException;
import com.example.restapi.model.LabResult;
import com.example.restapi.repository.LabResultRepository;

@Service
public class LabResultService {

    private final LabResultRepository labResultRepository;

    public LabResultService(LabResultRepository labResultRepository) {
        this.labResultRepository = labResultRepository;
    }

    public LabResult createLabResult(Long visitId, LabResult labResult) {
        labResult.setId(null);
        labResult.setVisitId(visitId);
        return labResultRepository.save(labResult);
    }

    public LabResult getLabResults(Long visitId) {
        return labResultRepository.findByVisitId(visitId)
            .orElseThrow(() -> new RecordNotFoundException("Lab result not found with visitId: " + visitId));
    }

    public LabResult updateLabResult(Long visitId, LabResult update) {
        return ((Optional<LabResult>) labResultRepository.findByVisitId(visitId))
                .map(existing -> {
                    existing.setType(update.getType());
                    existing.setPerformedDate(update.getPerformedDate());
                    existing.setDoctorNote(update.getDoctorNote());
                    existing.setResult(update.getResult());
                    existing.setConclusion(update.getConclusion());
                    return labResultRepository.save(existing);
                })
                .orElseThrow(() -> new RecordNotFoundException("Lab result not found with visitId: " + visitId));
    }

    public boolean deleteLabResult(Long visitId) {
        return labResultRepository.findByVisitId(visitId)
                .map(lab -> {
                    labResultRepository.delete(lab);
                    return true;
                })
                .orElse(false);
    }
    
}
