package com.example.restapi.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapi.model.LabRecord;

public interface LabRecordRepository extends JpaRepository<LabRecord, Long> {
    Optional<List<LabRecord>> findByVisitIdOrderByCreatedAtDesc(Long visitId);
    Optional<LabRecord> findFirstByVisitIdOrderByCreatedAtDesc(Long visitId);
    Optional<LabRecord> findByVisitIdAndType(Long visitId, String type);
}

