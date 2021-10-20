package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.PriceDto;
import com.eleks.academy.pharmagator.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/price")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @GetMapping
    public ResponseEntity<List<PriceDto>> findAllPrice() {
        List<PriceDto> allPrices = priceService.findAllPrices();
        return new ResponseEntity<>(allPrices, HttpStatus.OK);
    }

    //TODO: implement other endpoints



}
