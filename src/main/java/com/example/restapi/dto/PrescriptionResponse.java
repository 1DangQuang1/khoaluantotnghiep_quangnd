package com.example.restapi.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.restapi.model.PrescriptionItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionResponse {
    private Long id;
    private Long visitId;
    private String notes;
    private LocalDateTime createdAt;
    private List<PrescriptionItem> items;
}
