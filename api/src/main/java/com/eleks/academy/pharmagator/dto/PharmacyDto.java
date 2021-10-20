package com.eleks.academy.pharmagator.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyDto {

    private long id;
    private String name;
    private String medicineLinkTemplate;

}
