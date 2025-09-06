package com.example.restapi.controller;

import com.example.restapi.dto.InvoiceRequest;
import com.example.restapi.dto.InvoiceResponse;
import com.example.restapi.model.Invoice;
import com.example.restapi.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/visits")
// @CrossOrigin(origins = "*")
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

    @GetMapping("/invoice/{visitId}")
    public ResponseEntity<InvoiceResponse> getInvoice(@PathVariable Long visitId) {
        InvoiceResponse response = invoiceService.getInvoiceByVisitId(visitId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/invoice/{visitId}/save")
    public ResponseEntity<InvoiceResponse> saveInvoice(
            @PathVariable Long visitId,
            @RequestBody InvoiceRequest invoiceBody) {
        
        InvoiceResponse saved = invoiceService.saveInvoice(visitId, invoiceBody);
        return ResponseEntity.ok(saved);
    }
    
    
    
}
