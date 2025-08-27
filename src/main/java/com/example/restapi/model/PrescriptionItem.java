package com.example.restapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "emr_prescription_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    @JsonBackReference
    private Prescription prescription;


    @Column(name = "drug_code", nullable = false)
    private String drugCode;

    @Column(name = "dose_per_time")
    private Integer dosePerTime;   // số viên mỗi lần

    @Column(name = "times_per_day")
    private Integer timesPerDay;   // số lần/ngày

    @Column(name = "days")
    private Integer days;          // số ngày

    @Column(name = "note")
    private String note;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_code", referencedColumnName = "code", insertable = false, updatable = false)
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private Drug drug;
    
}

