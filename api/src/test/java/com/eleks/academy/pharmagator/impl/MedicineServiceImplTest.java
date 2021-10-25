package com.eleks.academy.pharmagator.impl;

import com.eleks.academy.pharmagator.dto.MedicinesDto;
import com.eleks.academy.pharmagator.dto.mappers.MedicineDtoMapper;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.exception.MedicineNotFoundException;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.service.impl.MedicineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicineServiceImplTest {

    private Medicine medicine1;
    private Medicine medicine2;

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private MedicineServiceImpl medicineService;

    @BeforeEach
    void beforeEach() {
        medicine1 = new Medicine(2021102401L, "Pharmacy findAll 1");
        medicine2 = new Medicine(2021102402L, "Pharmacy findAll 2");
    }

    @Test
    void testFindAllMedicine() {

        Mockito.when(medicineRepository.findAll()).thenReturn(List.of(medicine1, medicine2));

        var actual = medicineService.findAllMedicine();
        var expected = MedicineDtoMapper.toDto(List.of(medicine1, medicine2));

        assertFalse(actual.isEmpty());
        assertEquals(2, actual.size());
        assertEquals(expected, actual);

    }

    @Test
    void testFindAllMedicineIfNotExist() {

        when(medicineRepository.findAll()).thenReturn(Collections.emptyList());
        var actual = medicineService.findAllMedicine();
        assertTrue(actual.isEmpty());
        verify(medicineRepository, times(1)).findAll();

    }

    @Test
    void testFindMedicineByIdWhenIdExists() {

        Mockito.when(medicineRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(medicine1));
        var actual = medicineService.findMedicineById(2021102401L);
        var expected = MedicineDtoMapper.toDto(medicine1);

        assertEquals(expected, actual);

    }

    @Test
    void testFindMedicineByIdWhenIdNotExists() {

        Mockito.when(medicineRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        var medicineNotFoundException = assertThrows(MedicineNotFoundException.class,
                () -> medicineService.findMedicineById(Mockito.anyLong()));
        assertEquals(String.format("Medicine was not found for id: %s", 0), medicineNotFoundException.getMessage());
    }

    @Test
    void testSaveMedicine() {

        var expected = MedicineDtoMapper.toDto(medicine2);
        Mockito.when(medicineRepository.save(Mockito.any(Medicine.class))).thenReturn(medicine2);
        var actual = medicineService.saveMedicine(expected);

        assertEquals(expected, actual);

        verify(medicineRepository, times(1)).save(Mockito.any(Medicine.class));
        verifyNoMoreInteractions(medicineRepository);

    }

    @Test
    void testUpdateMedicine() {
        var expected = MedicineDtoMapper.toDto(medicine1);
        Mockito.when(medicineRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(medicine1));
        Mockito.when(medicineRepository.save(Mockito.any(Medicine.class))).thenReturn(medicine1);
        var actual = medicineService.updateMedicine(expected, Mockito.anyLong());

        assertEquals(expected, actual);

        verify(medicineRepository, times(1)).findById(Mockito.anyLong());
        verify(medicineRepository, times(1)).save(Mockito.any(Medicine.class));
        verifyNoMoreInteractions(medicineRepository);
    }

    @Test
    void testDeleteMedicine() {

        when(medicineRepository.findById(anyLong())).thenReturn(Optional.ofNullable(medicine1));
        doNothing().when(medicineRepository).delete(medicine1);

        medicineService.deleteMedicine(Mockito.anyLong());

        verify(medicineRepository, times(1)).findById(anyLong());
        verify(medicineRepository, times(1)).delete(medicine1);

    }

    @Test
    void testFindMedicineByTitle() {
        Mockito.when(medicineRepository.findByTitle(Mockito.anyString())).thenReturn(Optional.ofNullable(medicine1));
        var actual = medicineService.findMedicineByTitle(Mockito.anyString());

        assertEquals(medicine1, actual.get());
    }
}