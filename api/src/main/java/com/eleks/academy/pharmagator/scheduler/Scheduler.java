package com.eleks.academy.pharmagator.scheduler;

import com.eleks.academy.pharmagator.dataproviders.DataProvider;
import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.dataproviders.dto.mapper.EntityMapper;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class Scheduler {

    private final List<DataProvider> dataProvidersList;
    private final PriceRepository priceRepository;
    private final MedicineRepository medicineRepository;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void schedule() {
        log.info("Scheduler started at {}", Instant.now());
        dataProvidersList.stream()
                .flatMap(DataProvider::loadData)
                .forEach(this::storeToDatabase);
    }

    private void storeToDatabase(MedicineDto dto) {

        Optional<Medicine> existMedicine = medicineRepository.findByTitle(dto.getTitle());

        existMedicine.ifPresentOrElse(medicine -> {
            priceRepository
                    .findByPharmacyIdAndMedicineId(medicine.getId(), dto.getPharmacyId())
                    .ifPresentOrElse(price -> {
                        price.setPrice(dto.getPrice());
                        price.setUpdatedAt(Instant.now());
                        priceRepository.save(price);
                    }, () -> priceRepository.save(getPrice(dto, medicine)));
        }, () -> {
            Medicine medicine = new Medicine();
            EntityMapper.fromDto(dto, medicine);
            medicineRepository.save(medicine);
            medicineRepository.flush();
            Price price = getPrice(dto, medicine);
            priceRepository.save(price);
            priceRepository.flush();
        });
    }

    private Price getPrice(MedicineDto dto, Medicine medicine) {
        return Price.builder()
                .pharmacyId(dto.getPharmacyId())
                .medicineId(medicineRepository.findByTitle(medicine.getTitle()).get().getId())
                .price(dto.getPrice())
                .externalId(dto.getExternalId())
                .updatedAt(Instant.now())
                .build();
    }

}
