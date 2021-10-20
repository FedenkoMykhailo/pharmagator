package com.eleks.academy.pharmagator.service.impl;

import com.eleks.academy.pharmagator.dto.PharmacyDto;
import com.eleks.academy.pharmagator.dto.mappers.PharmacyDtoMapper;
import com.eleks.academy.pharmagator.exception.PharmacyNotFoundException;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;


    @Override
    public List<PharmacyDto> findAllPharmacy() {
        log.info("find all pharmacies ...");
        return PharmacyDtoMapper.toDto(pharmacyRepository.findAll());
    }

    @Override
    public PharmacyDto findPharmacyById(Long id) {
        log.info("find pharmacy by id: {} ", id);
        return PharmacyDtoMapper
                .toDto(pharmacyRepository
                        .findById(id)
                        .orElseThrow(() -> new PharmacyNotFoundException(String.format("farmacy not found for id: %s", id))));
    }

    @Override
    public PharmacyDto savePharmacy(PharmacyDto dto) {
        log.info("save pharmacy: {}", dto);
        return PharmacyDtoMapper.toDto(pharmacyRepository.save(PharmacyDtoMapper.fromDto(dto)));
    }

    @Override
    public PharmacyDto updatePharmacy(PharmacyDto dto, Long id) {
        PharmacyDto existPharmacy = findPharmacyById(id);
        existPharmacy.setName(dto.getName());
        existPharmacy.setMedicineLinkTemplate(dto.getMedicineLinkTemplate());
        return savePharmacy(existPharmacy);
    }

    @Override
    public void deletePharmacy(Long id) {
        log.info("delete pharmacy with id: {}", id);
        pharmacyRepository.delete(PharmacyDtoMapper.fromDto(findPharmacyById(id)));
    }
}
