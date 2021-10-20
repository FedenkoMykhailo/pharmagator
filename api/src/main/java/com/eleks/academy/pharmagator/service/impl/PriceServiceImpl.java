package com.eleks.academy.pharmagator.service.impl;

import com.eleks.academy.pharmagator.dto.PriceDto;
import com.eleks.academy.pharmagator.dto.mappers.PriceDtoMapper;
import com.eleks.academy.pharmagator.entities.PriceId;
import com.eleks.academy.pharmagator.exception.PriceNotFoundException;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import com.eleks.academy.pharmagator.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    @Override
    public List<PriceDto> findAllPrices() {
        log.info("find all price list");
        return PriceDtoMapper.toDto(priceRepository.findAll());
    }

    @Override
    public PriceDto findPriceById(Long pharmacyId, Long medicineId) {
        PriceId id = new PriceId(pharmacyId, medicineId);
        log.info("find price by id: {}", id);
        return PriceDtoMapper.toDto(priceRepository.findByPharmacyIdAndMedicineId(medicineId, pharmacyId).orElseThrow(()-> new PriceNotFoundException("price was not found for id: " + id)));
    }

    @Override
    public PriceDto savePrice(PriceDto dto) {
        log.info("save price: {}", dto);
        return PriceDtoMapper.toDto(priceRepository.save(PriceDtoMapper.fromDto(dto)));
    }

    @Override
    public PriceDto updatePrice(PriceDto dto, Long pharmacyId, Long medicineId) {
        PriceDto priceById = findPriceById(pharmacyId, medicineId);
        priceById.setPrice(dto.getPrice());
        priceById.setExternalId(dto.getExternalId());
        priceById.setUpdatedAt(Instant.now());
        return savePrice(priceById);
    }

    @Override
    public void deletePrice(Long pharmacyId, Long medicineId) {
        log.info("price deleted successfully");
        priceRepository.delete(PriceDtoMapper.fromDto(findPriceById(pharmacyId, medicineId)));

    }
}
