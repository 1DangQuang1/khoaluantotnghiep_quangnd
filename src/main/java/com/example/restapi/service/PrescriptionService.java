package com.example.restapi.service;

import com.example.restapi.dto.PrescriptionRequest;
import com.example.restapi.dto.PrescriptionResponse;
import com.example.restapi.model.Prescription;
import com.example.restapi.model.PrescriptionItem;
import com.example.restapi.model.Drug;
import com.example.restapi.model.Visit;

import com.example.restapi.repository.PrescriptionRepository;
import com.example.restapi.repository.VisitRepository;
import com.example.restapi.repository.DrugRepository;
import com.example.restapi.exceptions.DuplicateException;
import com.example.restapi.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final DrugRepository drugRepository;
    private final VisitRepository visitRepository;
    private final VisitService visitService;

    /**
     * Create new prescription for a visit
     */
    @Transactional
    public PrescriptionResponse createPrescription(Long visitId, PrescriptionRequest dto) {
        Prescription prescription = new Prescription();
        // kiểm tra visitId có tồn tại không
        if (!visitRepository.existsById(visitId)) {
            throw new NotFoundException("Visit not found: " + visitId);
        } else if (prescriptionRepository.findByVisitId(visitId).isPresent()) {
            throw new DuplicateException("Prescription already exists for visitId: " + visitId);
        }
        prescription.setVisitId(visitId);
        prescription.setDoctorId(dto.getDoctorId());
        prescription.setNotes(dto.getNotes());
        prescription.setCreatedAt(LocalDateTime.now());

        List<PrescriptionItem> items = dto.getItems().stream().map(reqItem -> {
            Drug drug = drugRepository.findByCode(reqItem.getDrugCode())
                    .orElseThrow(() -> new NotFoundException("Invalid drugCode: " + reqItem.getDrugCode()));

            PrescriptionItem item = new PrescriptionItem();
            item.setPrescription(prescription);
            item.setDrugCode(reqItem.getDrugCode());
            item.setDosePerTime(reqItem.getDosePerTime());
            item.setTimesPerDay(reqItem.getTimesPerDay());
            item.setDays(reqItem.getDays());
            item.setNote(reqItem.getNote());

            int totalQuantity = reqItem.getDosePerTime() * reqItem.getTimesPerDay() * reqItem.getDays();
            item.setTotalQuantity(totalQuantity);

            item.setDrug(drug); // map sang drug entity
            return item;
        }).collect(Collectors.toList());

        prescription.setItems(items);

        Prescription saved = prescriptionRepository.save(prescription);

        visitService.updateCurrentStep(visitId, Visit.VisitStep.PRESCRIPTION);
        visitService.updateStatus(visitId, Visit.VisitStatus.EXAMINING);

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public PrescriptionResponse getPrescriptionByVisit(Long visitId) {
        Prescription prescription = prescriptionRepository.findByVisitId(visitId)
                .orElseThrow(() -> new NotFoundException("VisitId not found: " + visitId));
        return mapToResponse(prescription);
    }

    /**
     * Update prescription
     */
    @Transactional
    public PrescriptionResponse updatePrescription(Long prescriptionId, PrescriptionRequest dto) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found: " + prescriptionId));

        prescription.setDoctorId(dto.getDoctorId());
        prescription.setNotes(dto.getNotes());

        // clear old items & replace
        prescription.getItems().clear();

        List<PrescriptionItem> newItems = dto.getItems().stream().map(reqItem -> {
            Drug drug = drugRepository.findByCode(reqItem.getDrugCode())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid drugCode: " + reqItem.getDrugCode()));

            PrescriptionItem item = new PrescriptionItem();
            item.setPrescription(prescription);
            item.setDrugCode(reqItem.getDrugCode());
            item.setDosePerTime(reqItem.getDosePerTime());
            item.setTimesPerDay(reqItem.getTimesPerDay());
            item.setDays(reqItem.getDays());
            item.setTotalQuantity(reqItem.getDosePerTime() * reqItem.getTimesPerDay() * reqItem.getDays());
            item.setDrug(drug);

            return item;
        }).collect(Collectors.toList());

        prescription.getItems().addAll(newItems);

        Prescription updated = prescriptionRepository.save(prescription);
        return mapToResponse(updated);
    }

    private PrescriptionResponse mapToResponse(Prescription prescription) {
        return PrescriptionResponse.builder()
                .id(prescription.getId())
                .visitId(prescription.getVisitId())
                .doctorId(prescription.getDoctorId())
                .notes(prescription.getNotes())
                .createdAt(prescription.getCreatedAt())
                .items(prescription.getItems()) // theo yêu cầu: dùng entity PrescriptionItem trực tiếp
                .build();
    }
}
