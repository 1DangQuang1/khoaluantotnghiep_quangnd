package com.example.restapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.restapi.exceptions.RecordNotFoundException;
import com.example.restapi.model.LabRecord;
import com.example.restapi.repository.LabRecordRepository;

@Service
public class LabRecordService {

    private final LabRecordRepository labResultRepository;

    public LabRecordService(LabRecordRepository labResultRepository) {
        this.labResultRepository = labResultRepository;
    }

    public LabRecord createLabResult(Long visitId, LabRecord labResult) {
        labResult.setId(null);
        labResult.setVisitId(visitId);
        return labResultRepository.save(labResult);
    }

    public List<LabRecord> getLabResults(Long visitId) {
        return labResultRepository.findByVisitId(visitId)
            .orElseThrow(() -> new RecordNotFoundException("Lab result not found with visitId: " + visitId));
    }

    public LabRecord updateLabResult(Long Id, LabRecord update) {
        return ((Optional<LabRecord>) labResultRepository.findById(Id))
                .map(existing -> {
                    existing.setType(update.getType());
                    existing.setPerformedDate(update.getPerformedDate());
                    existing.setDoctorNote(update.getDoctorNote());
                    existing.setResult(update.getResult());
                    existing.setConclusion(update.getConclusion());
                    return labResultRepository.save(existing);
                })
                .orElseThrow(() -> new RecordNotFoundException("Lab result not found with id: " + Id));
    }

    public boolean deleteLabResult(Long Id) {
        return labResultRepository.findById(Id)
                .map(lab -> {
                    labResultRepository.delete(lab);
                    return true;
                })
                .orElse(false);
    }
    
}
