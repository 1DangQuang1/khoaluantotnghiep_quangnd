package com.example.restapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restapi.exceptions.RecordNotFoundException;
import com.example.restapi.model.LabRecord;
import com.example.restapi.repository.LabServiceRepository;
import com.example.restapi.model.Visit;
import com.example.restapi.repository.LabRecordRepository;

@Service
public class LabRecordService {

    private final LabRecordRepository labResultRepository;
    private final VisitService visitService;
    private final LabServiceRepository labService;

    public LabRecordService(LabRecordRepository labResultRepository, VisitService visitService, LabServiceRepository labService) {
        this.labResultRepository = labResultRepository;
        this.visitService = visitService;
        this.labService = labService;
    }

    public List<String> getAllLabServiceNames() {
        return labService.getServiceNames();
    }

    public LabRecord createLabResult(Long visitId, LabRecord labResult) {
        labResult.setId(null);
        labResult.setVisitId(visitId);
        visitService.updateCurrentStep(visitId, Visit.VisitStep.PARACLINICAL);
        visitService.updateStatus(visitId, Visit.VisitStatus.EXAMINING);
        return labResultRepository.save(labResult);
    }

    public List<LabRecord> getLabResults(Long visitId) {
        return labResultRepository.findByVisitIdOrderByCreatedAtDesc(visitId)
            .orElseThrow(() -> new RecordNotFoundException("Lab results not found with visitId: " + visitId));
    }

    public LabRecord updateLabRecordByVisitId(Long visitId, LabRecord update) {
        return labResultRepository.findFirstByVisitIdOrderByCreatedAtDesc(visitId)
                .map(existing -> {
                    existing.setType(update.getType());
                    existing.setPerformedDate(update.getPerformedDate());
                    existing.setDoctorNote(update.getDoctorNote());
                    existing.setResult(update.getResult());
                    existing.setConclusion(update.getConclusion());
                    return labResultRepository.save(existing);
                })
                .orElseThrow(() -> new RecordNotFoundException(
                        "No lab result found with visit id: " + visitId));
    }

    public LabRecord updateLabRecordById(Long id, LabRecord update) {
        return labResultRepository.findById(id)
                .map(existing -> {
                    existing.setType(update.getType());
                    existing.setPerformedDate(update.getPerformedDate());
                    existing.setDoctorNote(update.getDoctorNote());
                    existing.setResult(update.getResult());
                    existing.setConclusion(update.getConclusion());
                    return labResultRepository.save(existing);
                })
                .orElseThrow(() -> new RecordNotFoundException(
                        "No lab result found with id: " + id));
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
