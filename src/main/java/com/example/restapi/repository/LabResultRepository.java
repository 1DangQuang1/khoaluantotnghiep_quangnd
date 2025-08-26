package com.example.restapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapi.model.LabResult;

public interface LabResultRepository extends JpaRepository<LabResult, Long> {
    Optional <LabResult> findByVisitId(Long visitId);
}

