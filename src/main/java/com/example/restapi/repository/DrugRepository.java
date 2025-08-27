package com.example.restapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapi.model.Drug;

public interface DrugRepository extends JpaRepository<Drug, Long> {
    Optional<Drug> findByCode(String code);
}
