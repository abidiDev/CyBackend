package com.example.demo.services;
import com.example.demo.dto.FirmTransactionDTO;
import com.example.demo.entities.Transaction;
import com.example.demo.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Slf4j
@AllArgsConstructor
public class TransactionService implements ITransactionService {

    TransactionRepository transactionRepository;
    private final Neo4jClient neo4jClient;

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAllTransactions();
    }

    @Override
    public List<Transaction> findByFilingYear(int filingYear) {
        return transactionRepository.findByFilingYear(filingYear);
    }

    @Override
    public List<Transaction> findByRfId(int rfId) {
        return transactionRepository.findByRfId(rfId);
    }

    @Override
    public List<Transaction> findByTechField(int techField) {
        return transactionRepository.findByTechField(techField);
    }

@Override
    public FirmTransactionDTO findFirmIdsByTransactionId(Long transactionId) {
        return neo4jClient.query(
                        "MATCH (f1:Firm)-[t:Transaction]->(f2:Firm) WHERE id(t) = $transactionId " +
                                "RETURN f1.firm_id AS firmId1, f2.firm_id AS firmId2")
                .bind(transactionId).to("transactionId")
                .fetchAs(FirmTransactionDTO.class)
                .mappedBy((typeSystem, record) -> new FirmTransactionDTO(
                        record.get("firmId1").asLong(),
                        record.get("firmId2").asLong()
                ))
                .one()
                .orElse(null);
    }

    public String findTransactionDirection(Long firmId, Long transactionId) {
        return transactionRepository.findTransactionDirectionByFirmIdAndTransactionId(firmId, transactionId);
    }
}