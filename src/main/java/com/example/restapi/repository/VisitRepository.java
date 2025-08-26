package com.example.restapi.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restapi.model.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    int countByDepartmentIdAndVisitDateAndShift(Long departmentId, LocalDate visitDate, Integer shift);
    List<Visit> findByStatusAndDepartmentIdAndVisitDate(Visit.VisitStatus status, Long departmentId, LocalDate date);
    Optional<Visit> findTopByPatientIdOrderByCreatedAtDesc(Long patientId);
}
