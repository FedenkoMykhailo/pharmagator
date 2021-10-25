package com.eleks.academy.pharmagator.dataproviders.impl;

import com.eleks.academy.pharmagator.dataproviders.DataProvider;
import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.dataproviders.dto.liki24.Liki24MedicineDto;
import com.eleks.academy.pharmagator.dataproviders.dto.liki24.Liki24MedicinesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.LongStream;
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

    private Stream<MedicineDto> fetchMedicineDto() {
        long pageIndex = 1L;

        BiConsumer<Long, List<Liki24MedicinesResponse>> fillListByMedicineResponse = (page1, medicinesResponseList1) -> {
            Liki24MedicinesResponse medicinesResponse = getLiki24MedicinesResponse(page1);
            medicinesResponseList1.add(medicinesResponse);
        };

        Liki24MedicinesResponse liki24MedicinesResponse = getLiki24MedicinesResponse(1L);

        if (liki24MedicinesResponse != null) {
            var start = System.currentTimeMillis();
            log.info("Start fetching: " + LocalDateTime.now());

            Long totalPages = liki24MedicinesResponse.getTotalPages();
            List<Liki24MedicinesResponse> medicinesResponseList = new ArrayList<>();

            LongStream.rangeClosed(pageIndex, totalPages)
                    .parallel()
                    .forEach(i -> fillListByMedicineResponse.accept(i, medicinesResponseList));

            log.info("End Fetching: " + LocalDateTime.now());
            log.info("Total fetching time: " + (System.currentTimeMillis() - start));
            return medicinesResponseList.stream()
                    .map(Liki24MedicinesResponse::getItems)
                    .flatMap(Collection::stream)
                    .map(this::mapToDataProviderMedicineDto);
        }
        return Stream.of();
    }

    private Liki24MedicinesResponse getLiki24MedicinesResponse(Long page) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("page", page)
                        .build())
                .retrieve().bodyToMono(Liki24MedicinesResponse.class)
                .block();
    }

    private MedicineDto mapToDataProviderMedicineDto(Liki24MedicineDto liki24MedicineDto) {
        BigDecimal price = liki24MedicineDto.getPrice() == null ? BigDecimal.ZERO : liki24MedicineDto.getPrice();
        return MedicineDto.builder()
                .externalId(liki24MedicineDto.getProductId())
                .title(liki24MedicineDto.getName())
                .price(price)
                .pharmacyId(2L)
                .build();
    }

}
