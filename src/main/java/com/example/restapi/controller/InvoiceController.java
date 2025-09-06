package com.example.restapi.controller;

import com.example.restapi.dto.InvoiceResponse;
import com.example.restapi.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visits")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    /**
     * Create a new invoice from a given visitId and return invoice with its items.
     */
    @PostMapping("/invoice/{visitId}")
    public ResponseEntity<InvoiceResponse> createInvoice(@PathVariable Long visitId) {
        InvoiceResponse invoiceResponse = invoiceService.createInvoiceFromVisit(visitId);
        return ResponseEntity.ok(invoiceResponse);
    }

    /**
     * (Optional) Fetch invoice by id
     */
    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponse> getInvoice(@PathVariable Long invoiceId) {
        // If you want to implement a "get by invoiceId" method later
        return ResponseEntity.notFound().build();
    }
}
