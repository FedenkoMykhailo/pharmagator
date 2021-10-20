package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.PharmacyDto;
import com.eleks.academy.pharmagator.service.PharmacyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy")
@RequiredArgsConstructor
@Api(tags = "Pharmacy management REST API")
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @ApiOperation(value = "Get all pharmacies list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PharmacyDto.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<PharmacyDto>> getAllPharmacy() {
        return new ResponseEntity<>(pharmacyService.findAllPharmacy(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get pharmacy by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PharmacyDto.class),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PharmacyDto> getPharmacyById(@PathVariable("id") Long pharmacyId) {
        return new ResponseEntity<>(pharmacyService.findPharmacyById(pharmacyId), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new pharmacy")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = PharmacyDto.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<PharmacyDto> savePharmacy(@RequestBody PharmacyDto dto) {
        return new ResponseEntity<>(pharmacyService.savePharmacy(dto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update pharmacy by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PharmacyDto.class),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PharmacyDto> updatePharmacy(@PathVariable("id") Long pharmacyId,
                                                      @RequestBody PharmacyDto dto) {
        return new ResponseEntity<>(pharmacyService.updatePharmacy(dto, pharmacyId), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete pharmacy by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePharmacy(@PathVariable("id") Long pharmacyId) {
        pharmacyService.deletePharmacy(pharmacyId);
        return new ResponseEntity<>("pharmacy was successfully deleted!", HttpStatus.OK);
    }

}
