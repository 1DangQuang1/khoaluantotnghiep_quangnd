package com.example.restapi.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.restapi.model.Invoice;


public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("SELECT COALESCE(MAX(i.id), 0) FROM Invoice i")
    Long getMaxId();

    Optional<Invoice> findByVisitId(Long visitId);
}
