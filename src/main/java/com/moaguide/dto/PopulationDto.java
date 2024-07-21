package com.moaguide.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PopulationDto {

    private int districtsId;

    private Date day;

    private BigDecimal total;
}
