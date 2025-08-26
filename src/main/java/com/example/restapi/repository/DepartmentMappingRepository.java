package com.example.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapi.model.Department;

public interface DepartmentMappingRepository extends JpaRepository<Department, Long> {
    boolean existsByDepartmentId(Long departmentId);
}
