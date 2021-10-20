package com.eleks.academy.pharmagator.service.impl;

import com.eleks.academy.pharmagator.dto.MedicinesDto;
import com.eleks.academy.pharmagator.dto.mappers.MedicineDtoMapper;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.exception.MedicineNotFoundException;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.service.MedicineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    @Override
    public List<MedicinesDto> findAllMedicine() {
        log.info("find all medicine");
        return MedicineDtoMapper.toDto(medicineRepository.findAll());
    }

    @Override
    public MedicinesDto findMedicineById(Long id) {
        log.info("find user with id: {}", id);
        return MedicineDtoMapper.toDto(medicineRepository
                .findById(id)
                .orElseThrow(() -> new MedicineNotFoundException(String.format("Medicine was not found for id: %s", id))));
    }

    @Override
    public MedicinesDto saveMedicine(MedicinesDto dto) {
        log.info("save new medicine: {}", dto);
        return MedicineDtoMapper.toDto(medicineRepository.save(MedicineDtoMapper.fromDto(dto)));
    }

    @Override
    public MedicinesDto updateMedicine(MedicinesDto dto, Long id) {
        log.info("update medicine with id: {}", id);
        MedicinesDto existingMedicine = findMedicineById(id);
        existingMedicine.setTitle(dto.getTitle());
        return MedicineDtoMapper.toDto(medicineRepository.save(MedicineDtoMapper.fromDto(existingMedicine)));
    }

    @Override
    public void deleteMedicine(Long id) {
        log.info("delete medicine with id: {}", id);
        medicineRepository.delete(MedicineDtoMapper.fromDto(findMedicineById(id)));
    }

    @Override
    public Optional<Medicine> findMedicineByTitle(String title) {
        return medicineRepository.findByTitle(title);
    }
}
