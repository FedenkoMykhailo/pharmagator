package com.eleks.academy.pharmagator.dto.mappers;

import com.eleks.academy.pharmagator.dto.MedicinesDto;
import com.eleks.academy.pharmagator.entities.Medicine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Mykhailo Fedenko
 * Medicine DTO mapper that used BeanUtils library to transform from one entity to another
 */
@Slf4j
public class MedicineDtoMapper {

    private MedicineDtoMapper() {}

    public static Medicine fromDto(MedicinesDto medicineDto) {
        log.debug("fromDto: map to medicine from medicineDto: {}", medicineDto);

        Medicine medicine = new Medicine();
        BeanUtils.copyProperties(medicineDto, medicine);

        return medicine;
    }

    public static List<Medicine> fromDto(List<MedicinesDto> medicineDtos) {
        return Objects.isNull(medicineDtos)
                ? null
                : medicineDtos.stream()
                .map(MedicineDtoMapper::fromDto)
                .collect(Collectors.toList());
    }

    public static MedicinesDto toDto(Medicine medicine) {
        log.debug("toDto: map medicineDto from medicine: {}", medicine);

        MedicinesDto medicineDto = new MedicinesDto();
        BeanUtils.copyProperties(medicine, medicineDto);

        return medicineDto;
    }

    public static List<MedicinesDto> toDto(List<Medicine> medicineList) {
        return Objects.isNull(medicineList)
                ? null
                : medicineList.stream()
                .map(MedicineDtoMapper::toDto)
                .collect(Collectors.toList());
    }

}
