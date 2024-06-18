package com.example.demo.services;

import com.example.demo.dto.FirmTransactionDTO;
import com.example.demo.entities.Transaction;

import java.util.List;

public interface ITransactionService {
    List<Transaction> findAll();

    List<Transaction> findByFilingYear(int filingYear);

    List<Transaction> findByRfId(int rfId);

    List<Transaction> findByTechField(int techField);

     FirmTransactionDTO findFirmIdsByTransactionId(Long transactionId);
    String findTransactionDirection(Long firmId, Long transactionId);


}
