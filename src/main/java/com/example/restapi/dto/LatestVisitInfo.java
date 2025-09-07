package com.example.restapi.dto;

import java.time.LocalDate;

public interface LatestVisitInfo {
    Long getId();
    String getFullName();
    String getCccd();
    String getGender();
    Integer getAge();
    String getInsuranceNumber();
    String getPhone();
    String getAddress();
    String getDepartmentName();
    String getStatus();
    LocalDate getLastVisitDate();
    Integer getVisitCount();
}

