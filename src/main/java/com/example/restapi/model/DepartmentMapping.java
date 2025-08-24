package com.example.restapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "emr_department_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "department_id", updatable = false, nullable = false)
    private Long departmentId;

    @Column(name = "department_name", nullable = false, unique = true, length = 50)
    private String departmentName;
}
