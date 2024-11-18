package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@Getter
public class DivideGraphDto {
    //최근 배당금
    private List<DivideCustomDto> divide;
    private double maxValue;
    private double minValue;
    private double maxRate;
    private double minRate;
}
