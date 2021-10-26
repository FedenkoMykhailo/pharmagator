package com.eleks.academy.pharmagator.impl;

import com.eleks.academy.pharmagator.dto.mappers.PriceDtoMapper;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.entities.PriceId;
import com.eleks.academy.pharmagator.exception.PriceNotFoundException;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import com.eleks.academy.pharmagator.service.impl.PriceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    private Price testPrice;

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceServiceImpl priceService;

    @BeforeEach
    void beforeEach() {
        testPrice = Price.builder()
                .pharmacyId(2L)
                .medicineId(2021102501L)
                .price(BigDecimal.valueOf(150))
                .externalId("20211025")
                .updatedAt(Instant.now())
                .build();
    }

    @Test
    void testFindAllPrices() {
        Mockito.when(priceRepository.findAll()).thenReturn(List.of(testPrice));
        var actual = priceService.findAllPrices();
        var expected = Stream.of(testPrice).map(PriceDtoMapper::toDto).collect(Collectors.toList());
        assertFalse(actual.isEmpty());
        assertEquals(1, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void testFindPriceByIdWhenIdExists() {
        Mockito.when(priceRepository.findByPharmacyIdAndMedicineId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(testPrice));
        var actual = priceService.findPriceById(Mockito.anyLong(), Mockito.anyLong());
        var expected = PriceDtoMapper.toDto(testPrice);

        assertEquals(expected, actual);
    }

    @Test
    void testFindPriceByIdWhenIdNotExists() {
        Mockito.when(priceRepository.findByPharmacyIdAndMedicineId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(Optional.empty());
        var priceNotFoundException = assertThrows(PriceNotFoundException.class,
                () -> priceService.findPriceById(Mockito.anyLong(), Mockito.anyLong()));
        PriceId priceId = new PriceId(0L, 0L);
        String expected = String.format("price was not found for id: %s", priceId);

        assertEquals(expected, priceNotFoundException.getMessage());
    }

    @Test
    void testSavePrice() {
        Mockito.when(priceRepository.save(Mockito.any(Price.class))).thenReturn(testPrice);
        var expected = PriceDtoMapper.toDto(testPrice);
        var actual = priceService.savePrice(expected);

        assertEquals(expected, actual);
    }

    @Test
    void testUpdatePrice() {
        Mockito.when(priceRepository.findByPharmacyIdAndMedicineId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(testPrice));
        Mockito.when(priceRepository.save(Mockito.any(Price.class))).thenReturn(testPrice);
        var expected = PriceDtoMapper.toDto(testPrice);
        var actual = priceService.updatePrice(expected, Mockito.anyLong(), Mockito.anyLong());

        assertEquals(expected, actual);
        Mockito.verify(priceRepository, Mockito.times(1)).findByPharmacyIdAndMedicineId(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(priceRepository, Mockito.times(1)).save(Mockito.any(Price.class));
        Mockito.verifyNoMoreInteractions(priceRepository);
    }

    @Test
    void testDeletePrice() {
        when(priceRepository.findByPharmacyIdAndMedicineId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(testPrice));
        doNothing().when(priceRepository).delete(testPrice);

        priceService.deletePrice(Mockito.anyLong(), Mockito.anyLong());

        verify(priceRepository, times(1)).findByPharmacyIdAndMedicineId(Mockito.anyLong(), Mockito.anyLong());
        verify(priceRepository, times(1)).delete(testPrice);
    }

}