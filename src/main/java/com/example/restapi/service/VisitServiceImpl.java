package com.example.restapi.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restapi.dto.VisitRequest;
import com.example.restapi.dto.VisitResponse;
import com.example.restapi.exceptions.PatientNotFoundException;
import com.example.restapi.exceptions.VisitNotFoundException;
import com.example.restapi.exceptions.DepartmentNotFoundException;
import com.example.restapi.model.Patient;
import com.example.restapi.model.Visit;
import com.example.restapi.repository.DepartmentMappingRepository;
import com.example.restapi.repository.PatientRepository;
import com.example.restapi.repository.VisitRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private static final Logger logger = LoggerFactory.getLogger(VisitServiceImpl.class);

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final DepartmentMappingRepository departmentMappingRepository;

    @Override
    @Transactional
    public VisitResponse createVisit(VisitRequest request) {
        Patient patient = patientRepository.findByCccd(request.getPatientCccd())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with CCCD " + request.getPatientCccd()));

        if (!departmentMappingRepository.existsByDepartmentId(request.getDepartmentId())) {
                throw new DepartmentNotFoundException("Invalid department_id: " + request.getDepartmentId());
        }
                

        Integer queueNo = visitRepository.countByDepartmentIdAndVisitDateAndShift(
                request.getDepartmentId(), LocalDate.now(), request.getShift()
        ) + 1;

        Visit visit = Visit.builder()
                .visitDate(LocalDate.now())
                .shift(request.getShift())
                .status(Visit.VisitStatus.WAITING)
                .currentStep(Visit.VisitStep.CLINICAL)
                .queueNo(queueNo)
                .patientId(patient.getId())
                .patientCccd(patient.getCccd())
                .departmentId(request.getDepartmentId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Visit saved = visitRepository.save(visit);
        return VisitResponse.fromEntity(saved);
    }

    @Override
    public List<VisitResponse> listVisits(Visit.VisitStatus status, Long departmentId, LocalDate date) {
        List<Visit> visits = visitRepository.findByStatusAndDepartmentIdAndVisitDate(
                status, departmentId, date != null ? date : LocalDate.now()
        );
        return visits.stream().map(VisitResponse::fromEntity).toList();
    }

    @Override
    public VisitResponse getVisit(Long visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with id " + visitId));
        return VisitResponse.fromEntity(visit);
    }

    @Override
    @Transactional
    public VisitResponse updateStatus(Long visitId, Visit.VisitStatus newStatus, Integer lockVersion) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with id " + visitId));

        if (!visit.getLockVersion().equals(lockVersion)) {
            throw new IllegalStateException("Visit was updated by another transaction");
        }

        visit.setStatus(newStatus);
        visit.setUpdatedAt(LocalDateTime.now());

        return VisitResponse.fromEntity(visitRepository.save(visit));
    }

    @Override
    @Transactional
    public VisitResponse assignDoctor(Long visitId, Long doctorId, Long roomId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with id " + visitId));

        visit.setDoctorId(doctorId);
        visit.setRoomId(roomId);
        visit.setStatus(Visit.VisitStatus.EXAMINING);
        visit.setUpdatedAt(LocalDateTime.now());

        return VisitResponse.fromEntity(visitRepository.save(visit));
    }


    @Override
    @Transactional
    public void cancelVisit(Long visitId, String cccd, String reason) {
        Visit visit;
    
        if (visitId != null) {
            visit = visitRepository.findById(visitId)
                    .orElseThrow(() -> new VisitNotFoundException("Visit not found with id " + visitId));
        } else if (cccd != null) {
            Patient patient = patientRepository.findByCccd(cccd)
                    .orElseThrow(() -> new PatientNotFoundException("Patient not found with CCCD " + cccd));
    
            visit = visitRepository.findTopByPatientIdOrderByCreatedAtDesc(patient.getId())
                    .orElseThrow(() -> new VisitNotFoundException("No visits found for patient with CCCD " + cccd));
        } else {
            throw new IllegalArgumentException("Either visitId or cccd must be provided");
        }
    
        visit.setStatus(Visit.VisitStatus.CANCELLED);
        visit.setUpdatedAt(LocalDateTime.now());
    
        visitRepository.save(visit);
        logger.info("Visit {} cancelled, reason={}, cccd={}", visit.getId(), reason, cccd);
    }

    @Override
    public VisitResponse getVisitByPatientCccd(String cccd) {
        Patient patient = patientRepository.findByCccd(cccd)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with CCCD " + cccd));

        Visit visit = visitRepository.findTopByPatientIdOrderByCreatedAtDesc(patient.getId())
                .orElseThrow(() -> new VisitNotFoundException("No visits found for patient with CCCD " + cccd));

        return VisitResponse.fromEntity(visit);
    }


}
