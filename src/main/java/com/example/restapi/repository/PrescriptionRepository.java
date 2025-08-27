package com.example.restapi.repository;
import  java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.restapi.model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Optional<Prescription> findByVisitId(Long visitId);
}   
