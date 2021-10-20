package com.eleks.academy.pharmagator.dto.mappers;

import com.eleks.academy.pharmagator.dto.PriceDto;
import com.eleks.academy.pharmagator.entities.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class PriceDtoMapper {

    private PriceDtoMapper() {}

    public static Price fromDto(PriceDto priceDto) {
        log.debug("fromDto: map to pharmacy from pharmacyDto: {}", priceDto);

        Price price = new Price();
        BeanUtils.copyProperties(priceDto, price);

        return price;
    }

    public static List<Price> fromDto(List<PriceDto> priceDtoList) {
        return Objects.isNull(priceDtoList)
                ? null
                : priceDtoList.stream()
                .map(PriceDtoMapper::fromDto)
                .collect(Collectors.toList());
    }

    public static PriceDto toDto(Price price) {
        log.debug("toDto: map pharmacyDto from pharmacy: {}", price);

        PriceDto priceDto = new PriceDto();
        BeanUtils.copyProperties(price, priceDto);

        return priceDto;
    }

    public static List<PriceDto> toDto(List<Price> priceList) {
        return Objects.isNull(priceList)
                ? null
                : priceList.stream()
                .map(PriceDtoMapper::toDto)
                .collect(Collectors.toList());
    }

}
