package com.example.restapi.service;

import java.time.LocalDate;
import java.time.Period;

import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.restapi.dto.LatestVisitInfo;
import com.example.restapi.dto.PatientListResponse;
import com.example.restapi.dto.PatientRequest;
import com.example.restapi.dto.PatientResponse;
import com.example.restapi.exceptions.DuplicateException;
import com.example.restapi.model.Patient;
import com.example.restapi.model.PatientSpecification;
import com.example.restapi.model.Visit;
import com.example.restapi.repository.PatientRepository;
import com.example.restapi.repository.VisitRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public Patient savePatient(Patient patient) {
        try {
            return patientRepository.save(patient);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateException("CCCD already exists: " + patient.getCccd());
        }
    }


    public PatientResponse createPatient(PatientRequest request) {
        Patient patient = mapToEntity(request);
        Patient saved = savePatient(patient);
        return mapToResponse(saved);
    }

    public Page<PatientListResponse> getPatientsWithFilter(
            String name,
            String cccd,
            String gender,
            Boolean bhyt,
            Visit.VisitStatus status,
            Long departmentId,
            Pageable pageable) {

        Specification<Patient> spec = Specification.unrestricted();

        if (name != null && !name.isEmpty()) spec = spec.and(PatientSpecification.hasName(name));
        if (cccd != null && !cccd.isEmpty()) spec = spec.and(PatientSpecification.hasCccd(cccd));
        if (gender != null && !gender.isEmpty()) spec = spec.and(PatientSpecification.hasGender(gender));
        if (bhyt != null) spec = spec.and(PatientSpecification.hasBhyt(bhyt));
        if (status != null) spec = spec.and(PatientSpecification.hasStatus(status));
        if (departmentId != null) spec = spec.and(PatientSpecification.hasDepartment(departmentId));

        return patientRepository.findAll(spec, pageable)
                .map(this::mapToResponseWithVisit);
    }

    // Get patient by id
    public PatientResponse getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    // Get patient by CCCD
    public PatientResponse getPatientByCccd(String cccd) {
        return patientRepository.findByCccd(cccd)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Patient not found with CCCD: " + cccd));
    }
    
    public PatientResponse updatePatientById(Long id, PatientRequest request) {
        Patient updated_patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    
        updateEntityFromRequest(updated_patient, request);
        return mapToResponse(patientRepository.save(updated_patient));
    }
    
    public PatientResponse updatePatientByCccd(String cccd, PatientRequest request) {
        Patient updated_patient = patientRepository.findByCccd(cccd)
                .orElseThrow(() -> new RuntimeException("Patient not found with cccd: " + cccd));
    
        updateEntityFromRequest(updated_patient, request);
        return mapToResponse(patientRepository.save(updated_patient));
    }
    
    public void deletePatientById(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
    
    public void deletePatientByCccd(String cccd) {
        Patient deleted_patient = patientRepository.findByCccd(cccd)
                .orElseThrow(() -> new RuntimeException("Patient not found with cccd: " + cccd));
        patientRepository.delete(deleted_patient);
    }
    


    

    // -------- Mapping helpers --------

    private Patient mapToEntity(PatientRequest request) {
        Patient.PatientBuilder builder = Patient.builder()
                .fullName(request.getFullName())
                .birthDate(request.getBirthDate())
                .gender(request.getGender())
                .cccd(request.getCccd())
                .bloodType(request.getBloodType())
                .phone(request.getPhone())
                .email(request.getEmail())
                .address(request.getAddress())
                .guardianName(request.getGuardianName())
                .guardianPhone(request.getGuardianPhone())
                .notes(request.getNotes())
                .medicalHistory(request.getMedicalHistory());
    
        if (request.getInsuranceNumber() != null) {
            builder.insuranceNumber(request.getInsuranceNumber());
        }
    
        return builder.build();
    }
    
    private void updateEntityFromRequest(Patient patient, PatientRequest request) {
        patient.setFullName(request.getFullName());
        patient.setBirthDate(request.getBirthDate());
        patient.setGender(request.getGender());
        patient.setCccd(request.getCccd());
        patient.setBloodType(request.getBloodType());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        patient.setAddress(request.getAddress());
        patient.setGuardianName(request.getGuardianName());
        patient.setGuardianPhone(request.getGuardianPhone());
        patient.setNotes(request.getNotes());
        patient.setInsuranceNumber(request.getInsuranceNumber());
        patient.setMedicalHistory(request.getMedicalHistory());
    }

    private PatientResponse mapToResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getFullName(),
                patient.getBirthDate(),
                patient.getGender(),
                patient.getCccd(),
                patient.getBloodType(),
                patient.getPhone(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getGuardianName(),
                patient.getGuardianPhone(),
                patient.getNotes(),
                patient.getInsuranceNumber() != null ? patient.getInsuranceNumber() :null,
                patient.getMedicalHistory()
        );
    }

    private PatientListResponse mapToResponseWithVisit(Patient patient) {
    Optional<LatestVisitInfo> latestVisitOpt = patientRepository.findLatestVisitInfoByPatientId(patient.getId());

    PatientListResponse response = PatientListResponse.builder()
            .patientId(patient.getId())
            .patientFullName(patient.getFullName())
            .patientCccd(patient.getCccd())
            .patientGender(patient.getGender())
            .patientAge(patient.getBirthDate() != null
                    ? String.valueOf(Period.between(patient.getBirthDate(), LocalDate.now()).getYears())
                    : null)
            .patientBhyt(patient.getInsuranceNumber())
            .phone(patient.getPhone())
            .address(patient.getAddress())
            .build();

    latestVisitOpt.ifPresent(latestVisit -> {
        response.setDepartmentName(latestVisit.getDepartmentName());
        response.setLatestVisitStatus(latestVisit.getStatus());
        response.setLatestVisitDate(latestVisit.getLastVisitDate() != null
                ? latestVisit.getLastVisitDate().toString()
                : null);
        response.setVisitCount(latestVisit.getVisitCount() != null ? latestVisit.getVisitCount() : 0);
    });

    return response;
}

    
    
}
