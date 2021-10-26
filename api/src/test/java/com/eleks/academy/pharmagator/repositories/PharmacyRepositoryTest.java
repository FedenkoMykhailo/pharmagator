package com.eleks.academy.pharmagator.repositories;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.projections.PharmacyLight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PharmacyRepositoryTest {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Test
    void findAllLight() {
        pharmacyRepository.deleteAll();
        Pharmacy pharmacy = new Pharmacy(1L, "Test Pharmacy 1", "http://test-template");
        pharmacyRepository.save(pharmacy);

        var actual = pharmacyRepository.findAllLight();

        assertEquals("Test Pharmacy 1", actual.get(0).getName());
    }
}