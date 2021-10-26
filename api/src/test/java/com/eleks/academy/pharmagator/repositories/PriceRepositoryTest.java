package com.eleks.academy.pharmagator.repositories;

import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.entities.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PriceRepositoryTest {

    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Test
    void findByPharmacyIdAndMedicineId() {

        medicineRepository.save(new Medicine(1L,"Test medicine"));
        pharmacyRepository.save(new Pharmacy(1L,"Test pharmacy", "Test template"));

        Price price = Price.builder()
                .pharmacyId(1L)
                .medicineId(1L)
                .externalId("2021102601")
                .updatedAt(Instant.now())
                .price(BigDecimal.valueOf(100))
                .build();
        priceRepository.save(price);

        var actual = priceRepository.findByPharmacyIdAndMedicineId(1L, 1L);
        assertTrue(actual.isPresent());
        assertEquals(price, actual.get());
    }
}