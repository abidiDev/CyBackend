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

    @Relationship(type = "Transaction", direction = Relationship.Direction.OUTGOING)
    @JsonManagedReference

    private List<Transaction> transactions;

}


