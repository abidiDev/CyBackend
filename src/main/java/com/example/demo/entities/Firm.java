package com.example.demo.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.List;

@Node("Firm")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Firm implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Property("firm_country")
    private String firmCountry;

    @Property("firm_id")
    private Long firmId;

    @Property("firm_name")
    private String firmName;

    @Property("firm_type")
    private String firmType;

    // We want to load both incoming and outgoing transactions when fetching a firm
    // to be able to compute transaction direction later in the service layer.
    // Using UNDIRECTED ensures Spring Data Neo4j retrieves relationships of
    // either direction.
    @Relationship(type = "Transaction", direction = Relationship.Direction.UNDIRECTED)
    @JsonManagedReference

    private List<Transaction> transactions;

}


