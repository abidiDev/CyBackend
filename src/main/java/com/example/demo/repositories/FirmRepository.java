package com.example.demo.repositories;

import com.example.demo.dto.AdvancedStatDTO;
import com.example.demo.entities.Firm;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FirmRepository extends Neo4jRepository<Firm, Long> {
    @Query("MATCH (f:Firm) RETURN f")
    List<Firm> findAllFirms();
    // Méthode pour trouver une entreprise par son firmId
    @Query("MATCH (f:Firm) WHERE f.firm_id = $firmId RETURN f")
    Optional<Firm> findByFirmId(Long firmId);
    // Retrieve both incoming and outgoing transactions related to the firm.
    // Using an undirected pattern ensures transactions are loaded regardless of
    // their direction so that the service layer can determine INCOMING or OUTGOING.
    @Query("MATCH (f:Firm {firm_id: $firmId}) " +
            "OPTIONAL MATCH (f)-[t:Transaction]-(o:Firm) " +
            "RETURN f, t, o")
    Optional<Firm> findByIdWithTransactions(@Param("firmId") Long firmId);

    List<Firm> findByFirmName(String firmName);
    @Query("MATCH (f:Firm {firm_country: $firmCountry}) RETURN f")
    List<Firm> findByFirmCountry(@Param("firmCountry") String firmCountry);

    @Query("MATCH (f:Firm {firm_type: $firmType}) RETURN f")
    List<Firm> findByFirmType(@Param("firmType") String firmType);


    //Statistics

    @Query("MATCH (f:Firm) RETURN count(f) AS numFirms")
    Long countFirms();

    @Query("MATCH ()-[t:Transaction]->() RETURN count(t) AS numTransactions")
    Long countTransactions();

    @Query("MATCH ()-[t:Transaction]->() RETURN avg(t.litigation_risk) AS averageLitigationRisk")
    Double calculateAverageLitigationRisk();
    @Query("MATCH (f:Firm)-[t:Transaction]->(o:Firm) RETURN avg(t.patent_quality) AS averagePatentQuality")
    Double calculateAveragePatentQuality();


}

