package com.example.restapi.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.restapi.model.Visit;

@Repository
public interface PatientReportRepository extends JpaRepository<Visit, Long> {
    
    @Query("SELECT COUNT(p) FROM Visit p WHERE p.status = 'WAITING' AND p.serviceId = 1")
    long countInpatients();    

    @Query("SELECT COUNT(p) FROM Visit p WHERE p.status = 'WAITING' AND p.serviceId = 1 AND p.visitDate = :yesterday")
    long countYesterdayInpatients(@Param("yesterday") LocalDate yesterday);


    @Query("SELECT COUNT(p) FROM Visit p WHERE p.status = 'WAITING' AND p.serviceId = 2")
    long countOutpatients();    

    @Query("SELECT COUNT(p) FROM Visit p WHERE p.status = 'WAITING' AND p.serviceId = 2 AND p.visitDate = :yesterday")
    long countYesterdayOutpatients(@Param("yesterday") LocalDate yesterday);


}
