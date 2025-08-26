package com.example.restapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "emr_clinical_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClinicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "visit_id", nullable = false)
    private Long visitId;

    @Column(name = "reason")
    private String reason; // Lý do khám

    @Column(name = "main_symptoms")
    private String mainSymptoms; // Triệu chứng chính

    @Column(name = "medical_history")
    private String medicalHistory; // Tiền sử bệnh

    @Column(name = "height")
    private Integer height; // cm

    @Column(name = "weight")
    private Integer weight; // kg

    @Column(name = "blood_pressure", length = 10)
    private String bloodPressure; // "120/80"

    @Column(name = "pulse_rate")
    private Integer pulseRate; // lần/phút

    @Column(name = "temperature")
    private Double temperature; // °C

    @Column(name = "respiratory_rate")
    private Integer respiratoryRate; // lần/phút

    @Column(name = "general_exam")
    private String generalExam;

    @Column(name = "cardio_exam")
    private String cardioExam;

    @Column(name = "respiratory_exam")
    private String respiratoryExam;

    @Column(name = "digestive_exam")
    private String digestiveExam;

    @Column(name = "neuro_exam")
    private String neuroExam;

    @Column(name = "musculoskeletal_exam")
    private String musculoskeletalExam;

    @Column(name = "dermatology_exam")
    private String dermatologyExam;

    @Column(name = "ent_exam")
    private String entExam;

    @Column(name = "eye_exam")
    private String eyeExam;

    @Column(name = "preliminary_diagnosis")
    private String preliminaryDiagnosis;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
