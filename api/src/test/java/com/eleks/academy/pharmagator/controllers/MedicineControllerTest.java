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
    private MedicinesDto medicine1;
    private MedicinesDto medicine2;

    @MockBean
    private MedicineService medicineService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mapper = new ObjectMapper();
        medicine1 = MedicinesDto.builder()
                .id(1L)
                .title("test title_1")
                .build();

        medicine2 = MedicinesDto.builder()
                .id(2L)
                .title("test title_2")
                .build();
    }

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(medicineService);
    }

    @Test
    void getAllMedicines() throws Exception {
        List<MedicinesDto> medicineList = List.of(medicine1, medicine2);
        when(medicineService.findAllMedicine()).thenReturn(medicineList);

        String actual = mockMvc.perform(get(API_MEDICINE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].id").value(medicine1.getId()))
                .andExpect(jsonPath("$[0].title").value(medicine1.getTitle()))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        String expected = mapper.writeValueAsString(medicineList);
        assertEquals(expected, actual);
        verify(medicineService, times(1)).findAllMedicine();
    }

    @Test
    void getMedicineById() throws Exception {

        when(medicineService.findMedicineById(Mockito.anyLong())).thenReturn(medicine2);

        String actual = mockMvc.perform(get(API_MEDICINE_URL + "/2/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(medicine2.getId()))
                .andExpect(jsonPath("$.title").value(medicine2.getTitle()))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        String expected = mapper.writeValueAsString(medicine2);
        assertEquals(expected, actual);
        verify(medicineService, times(1)).findMedicineById(2L);
    }

    @Test
    void saveMedicine() throws Exception {
        when(medicineService.saveMedicine(Mockito.any(MedicinesDto.class))).thenReturn(medicine1);
        String actual = mockMvc.perform(post(API_MEDICINE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(medicine1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(medicine1.getId()))
                .andExpect(jsonPath("$.title").value(medicine1.getTitle()))
                .andExpect(status().isCreated()).andDo(print()).andReturn().getResponse().getContentAsString();

        assertEquals(mapper.writeValueAsString(medicine1), actual);
        verify(medicineService, times(1)).saveMedicine(medicine1);
    }

    @Test
    void updateMedicine() throws Exception {

        when(medicineService.updateMedicine(any(MedicinesDto.class), Mockito.anyLong())).thenReturn(medicine2);

        String actual = mockMvc.perform(put(API_MEDICINE_URL + "/2/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(medicine2))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(medicine2.getId()))
                .andExpect(jsonPath("$.title").value(medicine2.getTitle()))
                .andExpect(status().isOk())
                .andDo(print()).andReturn().getResponse().getContentAsString();

        String expected = mapper.writeValueAsString(medicine2);

        assertEquals(expected, actual);
        verify(medicineService, times(1)).updateMedicine(medicine2, 2L);

    }

    @Test
    void deleteMedicine() throws Exception {

        String expected = "Medicine successfully deleted";
        doNothing().when(medicineService).deleteMedicine(Mockito.anyLong());

        String actual = mockMvc.perform(delete(API_MEDICINE_URL + "/2/")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$").exists())
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);
        verify(medicineService, times(1)).deleteMedicine(Mockito.anyLong());

    }
}