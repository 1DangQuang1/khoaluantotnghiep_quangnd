package com.example.restapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.restapi.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>,  JpaSpecificationExecutor<Patient> {
    Optional<Patient> findByCccd(String cccd);
}
