package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Year;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AveragePriceDto {
    private String cType;
    private LocalDate prdDe;
    private Double prdDt;
    // 연도기준
    private Year year;
}
