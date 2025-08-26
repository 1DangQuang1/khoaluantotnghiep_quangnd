package com.example.restapi.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapi.model.ClinicalRecord;

public interface ClinicalRecordRepository extends JpaRepository<ClinicalRecord, Long> {
    Optional<ClinicalRecord> findByVisitId(Long visitId);
}
