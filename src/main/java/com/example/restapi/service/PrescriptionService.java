// package com.example.restapi.service;

// import lombok.RequiredArgsConstructor;

// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.stream.Collectors;

// import com.example.restapi.dto.PrescriptionRequest;
// import com.example.restapi.dto.PrescriptionResponse;

// import com.example.restapi.repository.PrescriptionRepository;
// import com.example.restapi.repository.DrugRepository;
// import com.example.restapi.repository.PrescriptionItemRepository;


// @Service
// @RequiredArgsConstructor
// public class PrescriptionService {

//     private final PrescriptionRepository prescriptionRepository;
//     private final DrugRepository drugRepository;

//     /**
//      * Tạo mới đơn thuốc cho 1 visit
//      */
//     public PrescriptionResponse createPrescription(PrescriptionRequest request) {
//         // 1. Map request -> entity
//         Prescription prescription = new Prescription();
//         prescription.setVisitId(request.getVisitId());
//         prescription.setDoctorId(request.getDoctorId());
//         prescription.setNotes(request.getNotes());

//         List<PrescriptionItem> items = request.getItems().stream().map(i -> {
//             PrescriptionItem item = new PrescriptionItem();
//             item.setDrugCode(i.getDrugCode());
//             item.setDosePerTime(i.getDosePerTime());
//             item.setTimesPerDay(i.getTimesPerDay());
//             item.setDays(i.getDays());
//             item.setInstructions(i.getInstructions());
//             item.setPrescription(prescription);

//             // Calculate total quantity = dosePerTime * timesPerDay * days
//             int totalQty = i.getDosePerTime() * i.getTimesPerDay() * i.getDays();
//             item.setTotalQuantity(totalQty);

//             return item;
//         }).collect(Collectors.toList());

//         prescription.setItems(items);

//         // 2. Save vào DB
//         Prescription saved = prescriptionRepository.save(prescription);

//         // 3. Map entity -> response dto (enrich thuốc từ drug mapping)
//         return toResponseDto(saved);
//     }

//     /**
//      * Lấy chi tiết đơn thuốc theo ID
//      */
//     public PrescriptionResponseDto getPrescription(Long prescriptionId) {
//         Prescription prescription = prescriptionRepository.findById(prescriptionId)
//                 .orElseThrow(() -> new RuntimeException("Prescription not found"));

//         return toResponseDto(prescription);
//     }

//     /**
//      * Convert entity -> response dto
//      */
//     private PrescriptionResponseDto toResponseDto(Prescription prescription) {
//         PrescriptionResponseDto dto = new PrescriptionResponseDto();
//         dto.setId(prescription.getId());
//         dto.setVisitId(prescription.getVisitId());
//         dto.setDoctorId(prescription.getDoctorId());
//         dto.setNotes(prescription.getNotes());
//         dto.setCreatedAt(prescription.getCreatedAt());

//         List<PrescriptionResponseDto.PrescriptionItemResponseDto> items =
//                 prescription.getItems().stream().map(item -> {
//                     PrescriptionResponseDto.PrescriptionItemResponseDto iDto =
//                             new PrescriptionResponseDto.PrescriptionItemResponseDto();
//                     iDto.setId(item.getId());
//                     iDto.setDrugCode(item.getDrugCode());
//                     iDto.setDosePerTime(item.getDosePerTime());
//                     iDto.setTimesPerDay(item.getTimesPerDay());
//                     iDto.setDays(item.getDays());
//                     iDto.setInstructions(item.getInstructions());
//                     iDto.setTotalQuantity(item.getTotalQuantity());

//                     // Enrich từ drug mapping
//                     drugRepository.findByDrugCode(item.getDrugCode()).ifPresent(drug -> {
//                         iDto.setDrugName(drug.getDrugName());
//                         iDto.setActiveSubstance(drug.getActiveSubstance());
//                         iDto.setDosageForm(drug.getDosageForm());
//                         iDto.setStrength(drug.getStrength());
//                         iDto.setUnit(drug.getUnit());
//                     });

//                     return iDto;
//                 }).collect(Collectors.toList());

//         dto.setItems(items);

//         return dto;
//     }
// }
