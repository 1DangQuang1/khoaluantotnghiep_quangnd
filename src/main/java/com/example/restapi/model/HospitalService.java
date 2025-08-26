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
@Table(name = "emr_service_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "service_id", updatable = false, nullable = false)
    private Long serviceId;

    @Column(name = "service_name", nullable = false, unique = true, length = 50)
    private String serviceName;
}
