package com.example.demo.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;


import javax.persistence.GenerationType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;
@RelationshipProperties
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Transaction implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Property("filing_year")
    private int filingYear;

    @Property("grant_doc_num")
    private int grantDocNum;

    @Property("litigation")
    private int litigation;

    @Property("litigation_risk")
    private int litigationRisk;

    @Property("patent_quality")
    private int patentQuality;

    @Property("patent_value")
    private int patentValue;

    @Property("record_dt")
    private String recordDate;

    @Property("rf_id")
    private int rfId;

    @Property("tech_field")
    private int techField;

    @Property("year_dt")
    private int yearDt;

    @TargetNode
    @JsonBackReference
    private Firm firm;
    @Transient

    private Firm otherFirm ;
    @Transient
    private String direction;



}
