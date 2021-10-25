package com.eleks.academy.pharmagator.impl;

import com.eleks.academy.pharmagator.dto.mappers.PharmacyDtoMapper;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.exception.PharmacyNotFoundException;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.service.impl.PharmacyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PharmacyServiceImplTest {
    private Pharmacy pharmacy1;

    @Mock
    private PharmacyRepository pharmacyRepository;

    @InjectMocks
    private PharmacyServiceImpl pharmacyService;

    @BeforeEach
    void beforeEach() {
        pharmacy1 = new Pharmacy(2020102501L, "Liki24 test", "http://test-template");
    }

    @Test
    void testFindAllPharmacy() {
        Mockito.when(pharmacyRepository.findAll()).thenReturn(List.of(pharmacy1));
        var actual = pharmacyService.findAllPharmacy();
        var expected = Stream.of(pharmacy1).map(PharmacyDtoMapper::toDto).collect(Collectors.toList());

        assertFalse(actual.isEmpty());
        assertEquals(1, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void testFindPharmacyByIdWhenIdExists() {
        Mockito.when(pharmacyRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(pharmacy1));
        var actual = pharmacyService.findPharmacyById(Mockito.anyLong());
        var expected = PharmacyDtoMapper.toDto(pharmacy1);

        assertEquals(expected, actual);
    }

    @Test
    void testFindPharmacyByIdWhenIdNotExists() {
        Mockito.when(pharmacyRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.empty());
        var pharmacyNotFoundException = assertThrows(PharmacyNotFoundException.class, () -> pharmacyService.findPharmacyById(Mockito.anyLong()));
        String expected = String.format("farmacy not found for id: %s", 0);

        assertEquals(expected, pharmacyNotFoundException.getMessage());
    }

    @Test
    void testSavePharmacy() {
        Mockito.when(pharmacyRepository.save(Mockito.any(Pharmacy.class))).thenReturn(pharmacy1);
        var expected = PharmacyDtoMapper.toDto(pharmacy1);
        var actual = pharmacyService.savePharmacy(expected);

        assertEquals(expected, actual);
    }

    @Test
    void testUpdatePharmacy() {
        Mockito.when(pharmacyRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(pharmacy1));
        Mockito.when(pharmacyRepository.save(Mockito.any(Pharmacy.class))).thenReturn(pharmacy1);
        var expected = PharmacyDtoMapper.toDto(pharmacy1);
        var actual = pharmacyService.updatePharmacy(expected, Mockito.anyLong());

        assertEquals(expected, actual);
        Mockito.verify(pharmacyRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(pharmacyRepository, Mockito.times(1)).save(Mockito.any(Pharmacy.class));
        Mockito.verifyNoMoreInteractions(pharmacyRepository);
    }

    @Test
    void testDeletePharmacy() {
        when(pharmacyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(pharmacy1));
        doNothing().when(pharmacyRepository).delete(pharmacy1);

        pharmacyService.deletePharmacy(anyLong());

        verify(pharmacyRepository, times(1)).findById(anyLong());
        verify(pharmacyRepository, times(1)).delete(pharmacy1);
    }
}