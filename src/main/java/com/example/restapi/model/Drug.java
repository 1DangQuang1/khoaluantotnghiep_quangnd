package com.example.restapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "emr_drugs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Drug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    // <DRUG ABBREV><STRENGTH><FORM>
    @Column(name = "code", nullable = false, unique = true)
    private String code;   // ví dụ: PARA500, AMOX500

    @Column(name = "name", nullable = false)
    private String name;   // Paracetamol 500mg

    @Column(name = "active_substance")
    private String activeSubstance;

    @Column(name = "dosage_form")
    private String dosageForm; // viên nén, viên nang, gói...

    @Column(name = "strength")
    private String strength; // 500mg

    @Column(name = "unit")
    private String unit; // viên, gói, ml...

    @Column(name = "warnings")
    private String warnings;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "price")
    private Double price;

    @Column(name = "instructions")
    private String instructions; // Hướng dẫn sử dụng chung

    @OneToMany(mappedBy = "drug")
    @JsonBackReference
    private List<PrescriptionItem> items;

}
