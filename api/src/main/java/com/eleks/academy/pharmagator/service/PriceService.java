package com.eleks.academy.pharmagator.service;

import com.eleks.academy.pharmagator.dto.PriceDto;

import java.util.List;

public interface PriceService {

    List<PriceDto> findAllPrices();

    PriceDto findPriceById(Long pharmacyId, Long medicineId);

    PriceDto savePrice(PriceDto dto);

    PriceDto updatePrice(PriceDto dto, Long pharmacyId, Long medicineId);

    void deletePrice(Long pharmacyId, Long medicineId);

}
