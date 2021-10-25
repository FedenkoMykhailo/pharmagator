package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.mappers.PriceDtoMapper;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.service.PriceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {PriceController.class})
public class PriceControllerTest {
    public static final String API_PRICE_URL = "/api/v1/price";

    private ObjectMapper mapper;
    private Price price1;
    private Price price2;

    @MockBean
    private PriceService priceService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        price1 = Price.builder()
                .pharmacyId(2L)
                .medicineId(2021102501L)
                .externalId("2021102501")
                .price(BigDecimal.valueOf(150))
                .updatedAt(Instant.now())
                .build();

        price2 = Price.builder()
                .pharmacyId(2L)
                .medicineId(2021102502L)
                .externalId("2021102502")
                .price(BigDecimal.valueOf(100))
                .updatedAt(Instant.now())
                .build();
    }

    @Test
    void testFindAllPrice() throws Exception {
        var priceList = List.of(this.price1, price2);
        Mockito.when(priceService.findAllPrices()).thenReturn(PriceDtoMapper.toDto(priceList));

        var actual = mockMvc.perform(get(API_PRICE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].medicineId").value(2021102501L))
                .andExpect(jsonPath("$[0].pharmacyId").value(2L))
                .andExpect(jsonPath("$[0].price").value(BigDecimal.valueOf(150)))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        var expected = mapper.writeValueAsString(PriceDtoMapper.toDto(priceList));

        assertEquals(expected, actual);
    }


}
