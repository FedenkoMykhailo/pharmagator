package com.eleks.academy.pharmagator.service;

import com.eleks.academy.pharmagator.dto.PharmacyDto;

import java.util.List;

public interface PharmacyService {

    List<PharmacyDto> findAllPharmacy();

    PharmacyDto findPharmacyById(Long id);

    PharmacyDto savePharmacy(PharmacyDto dto);

    PharmacyDto updatePharmacy(PharmacyDto dto, Long id);

    void deletePharmacy(Long id);

}
