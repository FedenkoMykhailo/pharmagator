package com.eleks.academy.pharmagator.dataproviders.dto.mapper;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.entities.Price;

public class EntityMapper {

    private EntityMapper(){}

    public static Medicine fromDto (MedicineDto dto, Medicine medicine) {

        medicine.setTitle(dto.getTitle());

        return medicine;
    }

    public static Price fromDto (MedicineDto dto, Price price) {

        price.setPrice(dto.getPrice());
        price.setExternalId(dto.getExternalId());

        return price;
    }

}
