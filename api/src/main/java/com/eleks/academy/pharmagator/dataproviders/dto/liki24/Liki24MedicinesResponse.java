package com.eleks.academy.pharmagator.dataproviders.dto.liki24;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Liki24MedicinesResponse {

    private Long totalPages;

    private List<Liki24MedicineDto> items;

}
