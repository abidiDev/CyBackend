package com.example.demo.services;

import com.example.demo.dto.AdvancedStatDTO;
import com.example.demo.dto.FirmTransactionDTO;
import com.example.demo.entities.Firm;
import com.example.demo.entities.Transaction;
import com.example.demo.repositories.FirmRepository;
import lombok.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Getter
@Slf4j
@AllArgsConstructor
public class FirmService implements IFirmService {

    FirmRepository firmRepository;
    ITransactionService transactionService;

    private static final Logger logger = LoggerFactory.getLogger(FirmService.class);
    private final Neo4jClient neo4jClient;



    @Override
    public List<Firm> findAll() {
        return firmRepository.findAllFirms();
    }
    @Override
    public Optional<Firm> findByIdWithTransactions(Long firmId) {
        Optional<Firm> firm = firmRepository.findByIdWithTransactions(firmId);
        if (firm.isPresent()) {
            List<Transaction> transactions= firm.get().getTransactions();

            for (Transaction transaction : transactions) {
                FirmTransactionDTO firmTransaction = transactionService.findFirmIdsByTransactionId(transaction.getId());
                if (firmTransaction != null) {
                    if (firmTransaction.getFirmId2() != firmId) {
                        transaction.setDirection(transactionService.findTransactionDirection(firmId, transaction.getId()));

                        transaction.setOtherFirm(findByFirmId(firmTransaction.getFirmId2()).orElse(null));
                    } else {
                        transaction.setDirection(transactionService.findTransactionDirection(firmId, transaction.getId()));

                        transaction.setOtherFirm(findByFirmId(firmTransaction.getFirmId1()).orElse(null));
                    }
                }
            }

        } else {
            logger.warn("No firm found with firmId: {}", firmId);
        }
        return firm;    }

    @Override

    public Optional<Firm> findByFirmId(Long firmId) {
        return firmRepository.findByFirmId(firmId);
    }

    @Override
    public List<Firm> findByFirmName(String firmName) {
        return firmRepository.findByFirmName(firmName);
    }
    @Override
    public List<Firm> findByFirmCountry(String firmCountry) {
        return firmRepository.findByFirmCountry(firmCountry);
    }

    @Override
    public List<Firm> findByFirmType(String firmType) {
        return firmRepository.findByFirmType(firmType);
    }


//statistics

    public Long getNumberOfFirms() {
        return firmRepository.countFirms();
    }

    public Long getNumberOfTransactions() {
        return firmRepository.countTransactions();
    }


    public Double calculateAverageLitigationRisk() {
        return firmRepository.calculateAverageLitigationRisk();
    }

    public Double getAveragePatentQuality() {
        return firmRepository.calculateAveragePatentQuality();
    }

    //advanced stats

    public List<AdvancedStatDTO> getTransactionsByFirmType() {
        return (List<AdvancedStatDTO>) neo4jClient.query("MATCH (f:Firm)-[t:Transaction]->() WHERE f.firm_type =~ '^[A-Za-z\\s]+$' RETURN f.firm_type AS criteria, count(t) AS valueByCriteria ORDER BY criteria")
                .fetchAs(AdvancedStatDTO.class)
                .mappedBy((typeSystem, record) -> new AdvancedStatDTO(
                        record.get("criteria").asString(), // Utiliser asString() car firm_type est une chaîne
                        record.get("valueByCriteria").asLong()))
                .all();
    }

    public List<AdvancedStatDTO> getTransactionsByCountry() {
        return (List<AdvancedStatDTO>) neo4jClient.query("MATCH (f:Firm)-[t:Transaction]->() WHERE f.firm_country =~ '^[A-Za-z]+$' RETURN f.firm_country AS criteria, count(t) AS valueByCriteria ORDER BY criteria")
                .fetchAs(AdvancedStatDTO.class)
                .mappedBy((typeSystem, record) -> new AdvancedStatDTO(
                        record.get("criteria").asString(), // Utiliser asString() car firm_country est une chaîne
                        record.get("valueByCriteria").asLong()))
                .all();
    }


    public List<AdvancedStatDTO> getTransactionsByQuality() {
        return (List<AdvancedStatDTO>) neo4jClient.query("MATCH ()-[t:Transaction]->() RETURN t.patent_quality AS criteria, count(t) AS valueByCriteria ORDER BY criteria")
                .fetchAs(AdvancedStatDTO.class)
                .mappedBy((typeSystem, record) -> new AdvancedStatDTO(
                        record.get("criteria").asObject(), // Utiliser asObject() pour gérer différents types
                        record.get("valueByCriteria").asLong()))
                .all();
    }

}