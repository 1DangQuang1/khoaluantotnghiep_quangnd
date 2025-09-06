package com.example.restapi.repository;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.restapi.dto.InvoiceItemResponse;
import com.example.restapi.model.InvoiceItemResultMappingEntity;


@Repository
public interface InvoiceItemResponseRepository extends JpaRepository<InvoiceItemResultMappingEntity, Long> {
     @Query(name = "InvoiceItemResponse.findByVisitId", nativeQuery = true)
     List<InvoiceItemResponse> findByVisitId(@Param("visitId") Long visitId);
}
     
