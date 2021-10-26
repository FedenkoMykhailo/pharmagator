package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.MedicinesDto;
import com.eleks.academy.pharmagator.service.MedicineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {MedicineController.class})
class MedicineControllerTest {
    public static final String API_MEDICINE_URL = "/api/v1/medicine";

    private ObjectMapper mapper;
    private MedicinesDto testMedicineDto;

    @MockBean
    private MedicineService medicineService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mapper = new ObjectMapper();
        testMedicineDto = MedicinesDto.builder()
                .id(1L)
                .title("test title_1")
                .build();

    }

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(medicineService);
    }

    @Test
    void getAllMedicines() throws Exception {
        List<MedicinesDto> medicineList = List.of(testMedicineDto);
        when(medicineService.findAllMedicine()).thenReturn(medicineList);

        String actual = mockMvc.perform(get(API_MEDICINE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].id").value(testMedicineDto.getId()))
                .andExpect(jsonPath("$[0].title").value(testMedicineDto.getTitle()))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        String expected = mapper.writeValueAsString(medicineList);
        assertEquals(expected, actual);
        verify(medicineService, times(1)).findAllMedicine();
    }

    @Test
    void getMedicineById() throws Exception {

        when(medicineService.findMedicineById(Mockito.anyLong())).thenReturn(testMedicineDto);

        String actual = mockMvc.perform(get(API_MEDICINE_URL + "/{id}/", testMedicineDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(testMedicineDto.getId()))
                .andExpect(jsonPath("$.title").value(testMedicineDto.getTitle()))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        String expected = mapper.writeValueAsString(testMedicineDto);
        assertEquals(expected, actual);
        verify(medicineService, times(1)).findMedicineById(testMedicineDto.getId());
    }

    @Test
    void saveMedicine() throws Exception {
        when(medicineService.saveMedicine(Mockito.any(MedicinesDto.class))).thenReturn(testMedicineDto);
        String actual = mockMvc.perform(post(API_MEDICINE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testMedicineDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(testMedicineDto.getId()))
                .andExpect(jsonPath("$.title").value(testMedicineDto.getTitle()))
                .andExpect(status().isCreated()).andDo(print()).andReturn().getResponse().getContentAsString();

        assertEquals(mapper.writeValueAsString(testMedicineDto), actual);
        verify(medicineService, times(1)).saveMedicine(testMedicineDto);
    }

    @Test
    void updateMedicine() throws Exception {

        when(medicineService.updateMedicine(any(MedicinesDto.class), Mockito.anyLong())).thenReturn(testMedicineDto);

        String actual = mockMvc.perform(put(API_MEDICINE_URL + "/{id}/", testMedicineDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testMedicineDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(testMedicineDto.getId()))
                .andExpect(jsonPath("$.title").value(testMedicineDto.getTitle()))
                .andExpect(status().isOk())
                .andDo(print()).andReturn().getResponse().getContentAsString();

        String expected = mapper.writeValueAsString(testMedicineDto);

        assertEquals(expected, actual);
        verify(medicineService, times(1)).updateMedicine(testMedicineDto, testMedicineDto.getId());

    }

    @Test
    void deleteMedicine() throws Exception {

        String expected = "Medicine successfully deleted";
        doNothing().when(medicineService).deleteMedicine(Mockito.anyLong());

        String actual = mockMvc.perform(delete(API_MEDICINE_URL + "/{id}/", testMedicineDto.getId())
                        .accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$").exists())
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);
        verify(medicineService, times(1)).deleteMedicine(Mockito.anyLong());
    }

}