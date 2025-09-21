package com.example.restapi.repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Query("""
    SELECT v FROM Visit v
    WHERE (:status IS NULL OR v.status = :status)
      AND (:departmentId IS NULL OR v.departmentId = :departmentId)
      AND (:patientId IS NULL OR v.patientId = :patientId)
    """)
    List<Visit> findByFilters(@Param("status") Visit.VisitStatus status,
                            @Param("departmentId") Long departmentId,
                            @Param("patientId") Long patientId
                            );

    @Query("SELECT COUNT(p) FROM Visit p WHERE p.serviceId = 1 AND p.status = 'WAITING'")
    long countCurrentExaminingPatients();

    @Query("""
        SELECT COUNT(p) 
        FROM Visit p 
        WHERE p.serviceId = 1 
          AND p.status = 'WAITING'
          AND p.createdAt >= :yesterdayStart 
          AND p.createdAt < :todayStart
        """)
    long countYesterdayExaminingPatients(
      @Param("yesterdayStart") LocalDateTime yesterdayStart,
      @Param("todayStart") LocalDateTime todayStart
    );

    @Query("""
        SELECT d.departmentName, COUNT(v)
        FROM Visit v
        JOIN Department d ON v.departmentId = d.id
        GROUP BY d.departmentName
        order by COUNT(v) DESC
      """)
    List<Object[]> countByDepartment();

}
