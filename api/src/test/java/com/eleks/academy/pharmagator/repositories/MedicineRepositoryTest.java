package com.eleks.academy.pharmagator.repositories;

import com.eleks.academy.pharmagator.entities.Medicine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class MedicineRepositoryTest {

    @Autowired
    private MedicineRepository medicineRepository;

    @Test
    void findByTitle() {

        Medicine medicine = new Medicine(1L, "Medicine test 1");
        medicineRepository.save(medicine);
        var actual = medicineRepository.findByTitle("Medicine test 1");

        assertTrue(actual.isPresent());
        assertEquals(medicine, actual.get());

    }
}