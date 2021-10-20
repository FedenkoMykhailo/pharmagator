package com.eleks.academy.pharmagator.dataproviders.dto.ds;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterRequest {

    private Long page;
    private Long per;

}
