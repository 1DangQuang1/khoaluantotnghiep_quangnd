package com.example.restapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "emr_patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Full name is required")
    private String fullName;

    @Column(nullable = false)
    @PastOrPresent(message = "Birth date must be today or earlier")
    private LocalDate birthDate;

    @Column(nullable = false)
    @NotBlank(message = "Gender is required")
    private String gender;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "CCCD is required")
    private String cccd;

    private String bloodType;

    @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Invalid phone number")
    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    private String address;

    private String guardianName;

    @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Invalid guardian phone number")
    private String guardianPhone;

    private String notes;

    @Embedded
    private Insurance insurance;

    @Column(columnDefinition = "TEXT")
    private String medicalHistory;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;
}
