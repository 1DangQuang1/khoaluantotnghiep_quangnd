package com.example.restapi.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restapi.dto.VisitRequest;
import com.example.restapi.dto.VisitResponse;
import com.example.restapi.exceptions.NotFoundException;
import com.example.restapi.exceptions.PatientNotFoundException;
import com.example.restapi.exceptions.VisitNotFoundException;
import com.example.restapi.model.Patient;
import com.example.restapi.model.Visit;
import com.example.restapi.model.Visit.VisitStatus;
import com.example.restapi.repository.DepartmentMappingRepository;
import com.example.restapi.repository.PatientRepository;
import com.example.restapi.repository.ServiceMappingRepository;
import com.example.restapi.repository.VisitRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {


    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final DepartmentMappingRepository departmentMappingRepository;
    private final ServiceMappingRepository serviceMappingRepository;

    @Override
    @Transactional
    public VisitResponse createVisit(VisitRequest request) {
        Patient patient = patientRepository.findByCccd(request.getPatientCccd())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with CCCD " + request.getPatientCccd()));

        if (!departmentMappingRepository.existsByDepartmentId(request.getDepartmentId())) {
                throw new NotFoundException("Invalid department_id: " + request.getDepartmentId());
        }
        
        if (!serviceMappingRepository.existsByServiceId(request.getServiceId())) {
                throw new NotFoundException("Invalid service_id: " + request.getServiceId());
        }

        Integer queueNo = visitRepository.countByDepartmentIdAndVisitDateAndShift(
                request.getDepartmentId(), LocalDate.now(), request.getShift()
        ) + 1;

        Visit visit = Visit.builder()
                .visitDate(LocalDate.now())
                .shift(request.getShift())
                .status(Visit.VisitStatus.WAITING)
                .currentStep(Visit.VisitStep.WAITING)
                .queueNo(queueNo)
                .patientId(patient.getId())
                .patientCccd(patient.getCccd())
                .patientFullName(patient.getFullName())
                .doctorId(request.getDoctorId())
                .departmentId(request.getDepartmentId())
                .serviceId(request.getServiceId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Visit saved = visitRepository.save(visit);
        return VisitResponse.fromEntity(saved);
    }

    @Override
    public List<VisitResponse> listVisits(Visit.VisitStatus status, Long departmentId) {
        List<Visit> visits = visitRepository.findByFilters(
                status, departmentId
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
    public VisitResponse updateStatus(Long visitId, Visit.VisitStatus newStatus) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with id " + visitId));
        visit.setStatus(newStatus);
        visit.setUpdatedAt(LocalDateTime.now());

        return VisitResponse.fromEntity(visitRepository.save(visit));
    }

    @Override
    @Transactional
    public VisitResponse doneExamine(Long visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with id " + visitId));

        visit.setStatus(VisitStatus.DONE);
        visit.setUpdatedAt(LocalDateTime.now());

        return VisitResponse.fromEntity(visitRepository.save(visit));
    }


    @Override
    @Transactional
    public void cancelVisit(String cccd, String reason) {
        Visit visit;
    

        Patient patient = patientRepository.findByCccd(cccd)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with CCCD " + cccd));

        visit = visitRepository.findTopByPatientIdOrderByCreatedAtDesc(patient.getId())
                .orElseThrow(() -> new VisitNotFoundException("No visits found for patient with CCCD " + cccd));

        visit.setCancleReason(reason);
        visit.setStatus(Visit.VisitStatus.CANCELLED);
        visit.setUpdatedAt(LocalDateTime.now());
    
        visitRepository.save(visit);
    }

    @Override
    public VisitResponse getVisitByPatientCccd(String cccd) {
        Patient patient = patientRepository.findByCccd(cccd)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with CCCD " + cccd));

        Visit visit = visitRepository.findTopByPatientIdOrderByCreatedAtDesc(patient.getId())
                .orElseThrow(() -> new VisitNotFoundException("No visits found for patient with CCCD " + cccd));

        return VisitResponse.fromEntity(visit);
    }

    @Override
    public VisitResponse updateCurrentStep(Long visitId, Visit.VisitStep newStep ) {
                Visit visit = visitRepository.findById(visitId)
                        .orElseThrow(() -> new VisitNotFoundException("Visit not found with id " + visitId));
                visit.setCurrentStep(newStep);
                visit.setUpdatedAt(LocalDateTime.now());
        
                return VisitResponse.fromEntity(visitRepository.save(visit));
    }


}
