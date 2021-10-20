package com.eleks.academy.pharmagator.dto.mappers;

import com.eleks.academy.pharmagator.dto.PharmacyDto;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Mykhailo Fedenko
 * Pharmacy DTO mapper that used BeanUtils library to transform from one entity to another
 */
@Slf4j
public class PharmacyDtoMapper {

    private PharmacyDtoMapper() {}

    public static Pharmacy fromDto(PharmacyDto pharmacyDto) {
        log.debug("fromDto: map to pharmacy from pharmacyDto: {}", pharmacyDto);

        Pharmacy pharmacy = new Pharmacy();
        BeanUtils.copyProperties(pharmacyDto, pharmacy);

        return pharmacy;
    }

    public static List<Pharmacy> fromDto(List<PharmacyDto> pharmacyDtos) {
        return Objects.isNull(pharmacyDtos)
                ? null
                : pharmacyDtos.stream()
                .map(PharmacyDtoMapper::fromDto)
                .collect(Collectors.toList());
    }

    public static PharmacyDto toDto(Pharmacy pharmacy) {
        log.debug("toDto: map pharmacyDto from pharmacy: {}", pharmacy);

        PharmacyDto pharmacyDto = new PharmacyDto();
        BeanUtils.copyProperties(pharmacy, pharmacyDto);

        return pharmacyDto;
    }

    public static List<PharmacyDto> toDto(List<Pharmacy> pharmacyList) {
        return Objects.isNull(pharmacyList)
                ? null
                : pharmacyList.stream()
                .map(PharmacyDtoMapper::toDto)
                .collect(Collectors.toList());
    }

}
