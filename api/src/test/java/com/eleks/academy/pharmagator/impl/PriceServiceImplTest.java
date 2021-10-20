package com.eleks.academy.pharmagator.impl;

import com.eleks.academy.pharmagator.repositories.PriceRepository;
import com.eleks.academy.pharmagator.service.impl.PriceServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Disabled("Not implemented")
class PriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceServiceImpl priceService;

    @Test
    void testFindAllPrices() {
    }

    @Test
    void testFindPriceById() {
    }

    @Test
    void testSavePrice() {
    }

    @Test
    void testUpdatePrice() {
    }

    @Test
    void testDeletePrice() {
    }
}