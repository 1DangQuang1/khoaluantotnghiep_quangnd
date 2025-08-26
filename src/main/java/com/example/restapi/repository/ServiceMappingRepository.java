package com.example.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapi.model.HospitalService;

public interface ServiceMappingRepository extends JpaRepository<HospitalService, Long> {
    boolean existsByServiceId(Long serviceId);
}
