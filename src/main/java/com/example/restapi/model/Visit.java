package com.example.restapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "emr_visits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @Column(name = "shift", nullable = false)
    private Integer shift;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private VisitStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_step", nullable = false, length = 30)
    private VisitStep currentStep;

    @Column(name = "queue_no")
    private Integer queueNo;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "patient_cccd", nullable = false, length = 20)
    private String patientCccd;

    @Column(name = "patient_full_name", nullable = false, length = 200)
    private String patientFullName;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "cancle_reason", length = 500)
    private String cancleReason;

    @Column(name = "service_id", length = 100)
    private Long serviceId;

    @Column(name = "room_id")
    private Long roomId;

    @Version
    @Column(name = "lock_version")
    private Integer lockVersion;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.lockVersion == null) {
            this.lockVersion = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum VisitStatus {
        WAITING,
        EXAMINING,
        DONE,
        CANCELLED
    }

    public enum VisitStep {
        WAITING,
        CLINICAL,
        PARACLINICAL,
        PRESCRIPTION,
        BILLING,
        DONE
    }

}
