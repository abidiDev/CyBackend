package com.example.demo.services;

import com.example.demo.dto.AdvancedStatDTO;
import com.example.demo.dto.FirmTransactionDTO;
import com.example.demo.entities.Firm;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IFirmService {
    List<Firm> findAll();
    Optional<Firm> findByIdWithTransactions(Long id);
    Optional<Firm> findByFirmId(Long firmId);

    List<Firm> findByFirmName(String firmName);

    List<Firm> findByFirmCountry(String firmCountry);

    List<Firm> findByFirmType(String firmType);


     Long getNumberOfFirms();

     Long getNumberOfTransactions() ;

     Double calculateAverageLitigationRisk() ;

     Double getAveragePatentQuality() ;

    List<AdvancedStatDTO> getTransactionsByFirmType();
    List<AdvancedStatDTO> getTransactionsByCountry() ;

    List<AdvancedStatDTO> getTransactionsByQuality() ;

}
