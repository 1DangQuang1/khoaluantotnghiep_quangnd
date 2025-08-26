package com.example.restapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapi.model.LabRecord;

public interface LabRecordRepository extends JpaRepository<LabRecord, Long> {
    Optional <LabRecord> findByVisitId(Long visitId);
}

