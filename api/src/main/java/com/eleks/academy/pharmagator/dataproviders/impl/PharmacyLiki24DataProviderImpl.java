package com.eleks.academy.pharmagator.dataproviders.impl;

import com.eleks.academy.pharmagator.dataproviders.DataProvider;
import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.dataproviders.dto.liki24.Liki24MedicineDto;
import com.eleks.academy.pharmagator.dataproviders.dto.liki24.Liki24MedicinesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class PharmacyLiki24DataProviderImpl implements DataProvider {

    private final WebClient webClient;

    public PharmacyLiki24DataProviderImpl(@Qualifier("pharmacyLiki24WebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Stream<MedicineDto> loadData() {
        return fetchMedicineDto();
    }

    private Stream<MedicineDto> fetchMedicineDto () {
        long pageIndex = 1L;

        Liki24MedicinesResponse liki24MedicinesResponse = webClient.get()
                .retrieve().bodyToMono(Liki24MedicinesResponse.class)
                .block();

        if (liki24MedicinesResponse != null) {
            long page = pageIndex;
//            Long totalPages = liki24MedicinesResponse.getTotalPages();
            Long totalPages = 5L;
            List<Liki24MedicinesResponse> medicinesResponseList = new ArrayList<>();

            while (page <= totalPages) {
                Liki24MedicinesResponse medicinesResponse = webClient.get()
                        .uri("?page=" + page)
                        .retrieve().bodyToMono(Liki24MedicinesResponse.class)
                        .block();
                medicinesResponseList.add(medicinesResponse);
                page++;
            }
            return medicinesResponseList.stream()
                    .map(Liki24MedicinesResponse :: getItems)
                    .flatMap(Collection:: stream)
                    .map(this::mapToDataProviderMedicineDto);

        }
        return Stream.of();
    }

    private MedicineDto mapToDataProviderMedicineDto(Liki24MedicineDto liki24MedicineDto) {
        return MedicineDto.builder()
                .externalId(liki24MedicineDto.getProductId())
                .title(liki24MedicineDto.getName())
                .price(liki24MedicineDto.getPrice())
                .pharmacyId(2L)
                .build();
    }

}
