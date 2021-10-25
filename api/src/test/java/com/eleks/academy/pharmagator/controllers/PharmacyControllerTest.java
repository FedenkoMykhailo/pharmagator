package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.MedicinesDto;
import com.eleks.academy.pharmagator.dto.PharmacyDto;
import com.eleks.academy.pharmagator.dto.mappers.PharmacyDtoMapper;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.service.PharmacyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {PharmacyController.class})
public class PharmacyControllerTest {
    public static final String API_PHARMACY_URL = "/pharmacies";

    private ObjectMapper mapper;
    private Pharmacy pharmacy1;
    private Pharmacy pharmacy2;

    @MockBean
    private PharmacyService pharmacyService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        pharmacy1 = new Pharmacy(1L, "Test pharmacy 1", "http://test-link-template-1");
        pharmacy2 = new Pharmacy(2L, "Test pharmacy 2", "http://test-link-template-2");

        mapper = new ObjectMapper();
    }

    @Test
    void testGetAllPharmacy() throws Exception {
        var pharmacyList = Stream.of(pharmacy1, pharmacy2).collect(Collectors.toList());
        Mockito.when(pharmacyService.findAllPharmacy()).thenReturn(PharmacyDtoMapper.toDto(pharmacyList));

        String actual = mockMvc.perform(get(API_PHARMACY_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test pharmacy 1"))
                .andExpect(jsonPath("$[0].medicineLinkTemplate").value("http://test-link-template-1"))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        var expected = mapper.writeValueAsString(PharmacyDtoMapper.toDto(pharmacyList));
        assertEquals(expected, actual);
    }

    @Test
    void testGetPharmacyById() throws Exception {

        when(pharmacyService.findPharmacyById(Mockito.anyLong())).thenReturn(PharmacyDtoMapper.toDto(pharmacy1));

        String actual = mockMvc.perform(get(API_PHARMACY_URL + "/1/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(pharmacy1.getId()))
                .andExpect(jsonPath("$.name").value(pharmacy1.getName()))
                .andExpect(jsonPath("$.medicineLinkTemplate").value(pharmacy1.getMedicineLinkTemplate()))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        String expected = mapper.writeValueAsString(pharmacy1);
        assertEquals(expected, actual);
        verify(pharmacyService, times(1)).findPharmacyById(1L);
    }

    @Test
    void testSavePharmacy() throws Exception {
        when(pharmacyService.savePharmacy(Mockito.any(PharmacyDto.class))).thenReturn(PharmacyDtoMapper.toDto(pharmacy1));
        String actual = mockMvc.perform(post(API_PHARMACY_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pharmacy1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(pharmacy1.getId()))
                .andExpect(jsonPath("$.name").value(pharmacy1.getName()))
                .andExpect(jsonPath("$.medicineLinkTemplate").value(pharmacy1.getMedicineLinkTemplate()))
                .andExpect(status().isCreated()).andDo(print()).andReturn().getResponse().getContentAsString();

        assertEquals(mapper.writeValueAsString(pharmacy1), actual);
        verify(pharmacyService, times(1)).savePharmacy(Mockito.any(PharmacyDto.class));
    }

    @Test
    void testUpdatePharmacy() throws Exception {

        when(pharmacyService.updatePharmacy(any(PharmacyDto.class), Mockito.anyLong())).thenReturn(PharmacyDtoMapper.toDto(pharmacy1));

        String actual = mockMvc.perform(put(API_PHARMACY_URL + "/1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(PharmacyDtoMapper.toDto(pharmacy1)))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(pharmacy1.getId()))
                .andExpect(jsonPath("$.name").value(pharmacy1.getName()))
                .andExpect(jsonPath("$.medicineLinkTemplate").value(pharmacy1.getMedicineLinkTemplate()))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        String expected = mapper.writeValueAsString(PharmacyDtoMapper.toDto(pharmacy1));

        assertEquals(expected, actual);
        verify(pharmacyService, times(1)).updatePharmacy(Mockito.any(PharmacyDto.class), Mockito.anyLong());

    }

    @Test
    void deleteMedicine() throws Exception {

        String expected = "pharmacy was successfully deleted!";
        doNothing().when(pharmacyService).deletePharmacy(Mockito.anyLong());

        String actual = mockMvc.perform(delete(API_PHARMACY_URL + "/1/")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$").exists())
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);
        verify(pharmacyService, times(1)).deletePharmacy(Mockito.anyLong());

    }

}
