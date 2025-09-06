package com.example.restapi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.restapi.model.LabService;

@Repository
public interface LabServiceRepository extends JpaRepository<LabService, Long> {
    @Query("SELECT l.serviceName FROM LabService l")
    List<String> getServiceNames();
}
