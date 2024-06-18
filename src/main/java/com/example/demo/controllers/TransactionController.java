package com.example.demo.controllers;


import com.example.demo.dto.FirmTransactionDTO;
import com.example.demo.entities.Firm;
import com.example.demo.entities.Transaction;
import com.example.demo.services.ITransactionService;
import com.example.demo.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RequestMapping("/api/transactions")
@RestController

public class TransactionController {

   ITransactionService transactionService;


    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.findAll();
    }

    @GetMapping("/filingYear/{filingYear}")
    public List<Transaction> getTransactionsByFilingYear(@PathVariable int filingYear) {
        return transactionService.findByFilingYear(filingYear);
    }

    @GetMapping("/rfId/{rfId}")
    public List<Transaction> getTransactionsByRfId(@PathVariable int rfId) {
        return transactionService.findByRfId(rfId);
    }
    @GetMapping("/{transactionId}/firms")
    public ResponseEntity<FirmTransactionDTO> getFirmIdsByTransactionId(@PathVariable Long transactionId) {
        FirmTransactionDTO firmTransactionDTO = transactionService.findFirmIdsByTransactionId(transactionId);
        if (firmTransactionDTO != null) {
            return ResponseEntity.ok(firmTransactionDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/techField/{techField}")
    public List<Transaction> getTransactionsByTechField(@PathVariable int techField) {
        return transactionService.findByTechField(techField);
    }
}
