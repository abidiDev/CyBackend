package com.example.demo.repositories;

import com.example.demo.dto.FirmTransactionDTO;
import com.example.demo.entities.Firm;
import com.example.demo.entities.Transaction;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TransactionRepository extends Neo4jRepository<Transaction, Long> {

    @Query("MATCH (a)-[t:Transaction]->(b) RETURN t, a, b")
    List<Transaction> findAllTransactions();

    @Query("MATCH (a)-[t:Transaction]->(b) WHERE t.filing_year = $filingYear RETURN t, a, b")
    List<Transaction> findByFilingYear(@Param("filingYear") int filingYear);

    @Query("MATCH (a)-[t:Transaction]->(b) WHERE t.rf_id = $rfId RETURN t, a, b")
    List<Transaction> findByRfId(@Param("rfId") int rfId);

    @Query("MATCH (a)-[t:Transaction]->(b) WHERE t.tech_field = $techField RETURN t, a, b")
    List<Transaction> findByTechField(@Param("techField") int techField);
    @Query("MATCH (f1:Firm)-[t:Transaction]->(f2:Firm) WHERE id(t) = $transactionId RETURN f1, f2")
    List<Firm> findFirmsByTransactionId(@Param("transactionId") Long transactionId);

    @Query("MATCH (f:Firm)-[t:Transaction]->(o:Firm) " +
            "WHERE id(t) = $transactionId AND f.firm_id = $firmId " +
            "RETURN 'OUTGOING' AS direction " +
            "UNION " +
            "MATCH (o:Firm)-[t:Transaction]->(f:Firm) " +
            "WHERE id(t) = $transactionId AND f.firm_id = $firmId " +
            "RETURN 'INCOMING' AS direction")
    String findTransactionDirectionByFirmIdAndTransactionId(@Param("firmId") Long firmId, @Param("transactionId") Long transactionId);


}