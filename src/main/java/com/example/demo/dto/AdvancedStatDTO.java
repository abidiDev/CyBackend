package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.*;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class AdvancedStatDTO {
    private Object  criteria;
    private Long valueByCriteria;
}
