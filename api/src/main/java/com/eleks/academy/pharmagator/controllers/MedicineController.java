package com.eleks.academy.pharmagator.controllers;


import com.eleks.academy.pharmagator.dto.MedicinesDto;
import com.eleks.academy.pharmagator.service.MedicineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/medicine")
@RequiredArgsConstructor
@Api(tags = "Medicine management REST API")
public class MedicineController {

    private final MedicineService medicineService;

    @ApiOperation(value = "Get all medicine list")
    @GetMapping
    public ResponseEntity<List<MedicinesDto>> getAllMedicines() {
        return new ResponseEntity<>(medicineService.findAllMedicine(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get medicine by ID")
    @GetMapping("/{id}")
    public ResponseEntity<MedicinesDto> getMedicineById(@PathVariable("id") Long medicineId) {
        return new ResponseEntity<>(medicineService.findMedicineById(medicineId), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new medicine")
    @PostMapping
    public ResponseEntity<MedicinesDto> saveMedicine(@RequestBody MedicinesDto dto) {
        return new ResponseEntity<>(medicineService.saveMedicine(dto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update medicine")
    @PutMapping("/{id}")
    public ResponseEntity<MedicinesDto> updateMedicine(@PathVariable("id") Long medicineId,
                                                      @RequestBody MedicinesDto dto) {
        return new ResponseEntity<>(medicineService.updateMedicine(dto, medicineId), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete medicine by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedicine(@PathVariable("id") Long medicineId) {
        medicineService.deleteMedicine(medicineId);
        return new ResponseEntity<>("Medicine successfully deleted", HttpStatus.OK);
    }
}
