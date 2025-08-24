package com.example.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapi.model.DepartmentMapping;

public interface DepartmentMappingRepository extends JpaRepository<DepartmentMapping, Long> {
    boolean existsByDepartmentId(Long departmentId);
}
