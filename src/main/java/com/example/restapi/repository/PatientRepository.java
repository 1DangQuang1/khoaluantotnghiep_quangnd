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
        WITH ranked AS (
            SELECT ev.patient_id,
                dm.department_name,
                ev.status,
                ev.visit_date,
                COUNT(*) OVER (PARTITION BY ev.patient_id) AS visit_count,
                ROW_NUMBER() OVER (PARTITION BY ev.patient_id ORDER BY ev.visit_date DESC) AS rn
            FROM ehrm.emr_visits ev
            LEFT JOIN ehrm.emr_department_mapping dm ON ev.department_id = dm.department_id
            WHERE ev.patient_id = :patientId
        )
        SELECT ep.id,
            ep.full_name,
            ep.cccd,
            ep.gender,
            EXTRACT(YEAR FROM age(ep.birth_date)) AS age,
            ep.insurance_number,
            ep.phone,
            ep.address,
            r.department_name,
            r.status,
            r.visit_date AS last_visit_date,
            r.visit_count
        FROM ehrm.emr_patients ep
        JOIN ranked r ON ep.id = r.patient_id
        WHERE r.rn = 1;
    """, nativeQuery = true)
    Optional<LatestVisitInfo> findLatestVisitInfoByPatientId(@Param("patientId") Long patientId);
}
