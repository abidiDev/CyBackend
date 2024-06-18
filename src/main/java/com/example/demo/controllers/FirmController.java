package com.example.demo.controllers;

import com.example.demo.dto.AdvancedStatDTO;
import com.example.demo.dto.FirmTransactionDTO;
import com.example.demo.entities.Firm;
import com.example.demo.services.IFirmService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("FirmController")
@RestController
@AllArgsConstructor
public class FirmController {

    private final IFirmService firmService;

    @GetMapping
    public ResponseEntity<List<Firm>> getAllFirms() {
        List<Firm> firms = firmService.findAll();
        return ResponseEntity.ok(firms);
    }

    @GetMapping("/{firmName}")
    public ResponseEntity<List<Firm>> getFirmsByFirmName(@PathVariable String firmName) {
        List<Firm> firms = firmService.findByFirmName(firmName);
        return ResponseEntity.ok(firms);
    }

    @GetMapping("withTransactions/{firmId}")
    public ResponseEntity<Firm> getFirmWithTransactions(@PathVariable Long firmId) {
        Optional<Firm> firm = firmService.findByIdWithTransactions(firmId);
        return firm.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/country/{firmCountry}")
    public ResponseEntity<List<Firm>> getFirmsByFirmCountry(@PathVariable String firmCountry) {
        List<Firm> firms = firmService.findByFirmCountry(firmCountry);
        return ResponseEntity.ok(firms);
    }

    @GetMapping("/type/{firmType}")
    public ResponseEntity<List<Firm>> getFirmsByFirmType(@PathVariable String firmType) {
        List<Firm> firms = firmService.findByFirmType(firmType);
        return ResponseEntity.ok(firms);
    }
    @GetMapping("/firms")
    public ResponseEntity<Long> getNumberOfFirms() {
        Long result = firmService.getNumberOfFirms();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/transactions")
    public ResponseEntity<Long> getNumberOfTransactions() {
        Long result = firmService.getNumberOfTransactions();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/litigation-Risk")
    public ResponseEntity<Double> calculateAverageLitigationRisk() {
        Double result = firmService.calculateAverageLitigationRisk();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/average-patent-quality")
    public ResponseEntity<Double> getAveragePatentQuality() {
        Double result = firmService.getAveragePatentQuality();
        return ResponseEntity.ok(result);
    }

    // Advanced stats

    @GetMapping("/transactions-par-qualite")
    public ResponseEntity<List<AdvancedStatDTO>> getTransactionsByQuality() {
        List<AdvancedStatDTO> result = firmService.getTransactionsByQuality();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/transactions-par-type")
    public ResponseEntity<List<AdvancedStatDTO>> getTransactionsByFirmType() {
        List<AdvancedStatDTO> result = firmService.getTransactionsByFirmType();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/transactions-par-pays")
    public ResponseEntity<List<AdvancedStatDTO>> getTransactionsByCountry() {
        List<AdvancedStatDTO> result = firmService.getTransactionsByCountry();
        return ResponseEntity.ok(result);
    }
}