package com.example.restapi.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.restapi.model.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    int countByDepartmentIdAndVisitDateAndShift(Long departmentId, LocalDate visitDate, Integer shift);
    Optional<Visit> findTopByPatientIdOrderByCreatedAtDesc(Long patientId);

    @Query(value ="""
    SELECT v FROM Visit v
    WHERE (:status IS NULL OR v.status = :status)
      AND (:departmentId IS NULL OR v.departmentId = :departmentId)
    """, nativeQuery= true)
    List<Visit> findByFilters(@Param("status") Visit.VisitStatus status,
                            @Param("departmentId") Long departmentId);

}
