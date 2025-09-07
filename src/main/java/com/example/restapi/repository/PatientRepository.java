package com.example.restapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.restapi.dto.LatestVisitInfo;
import com.example.restapi.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>,  JpaSpecificationExecutor<Patient> {
    Optional<Patient> findByCccd(String cccd);
    @Query(value = """
        WITH base AS (
            SELECT ev.patient_id, dm.department_name, ev.visit_date, ev.status,
                COUNT(*) OVER (PARTITION BY ev.patient_id) AS visit_count
            FROM ehrm.emr_visits ev
            LEFT JOIN ehrm.emr_department_mapping dm ON ev.department_id = dm.department_id
            WHERE ev.patient_id = :patientId
        ), nx_base AS (
            SELECT patient_id, department_name, MAX(visit_date) AS visit_date, status, visit_count
            FROM base
            GROUP BY patient_id, department_name, status, visit_count
        )
        SELECT ep.id, ep.full_name, ep.cccd , ep.gender ,
            EXTRACT(YEAR FROM age(ep.birth_date)) AS age,
            ep.insurance_number,
            ep.phone, ep.address ,
            n.department_name,
            n.status,
            n.visit_date AS last_visit_date,
            n.visit_count
        FROM ehrm.emr_patients ep
        JOIN nx_base n ON ep.id = n.patient_id
    """, nativeQuery = true)
    Optional<LatestVisitInfo> findLatestVisitInfoByPatientId(@Param("patientId") Long patientId);
}
