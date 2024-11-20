package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DivideGraphDto {
    private List<DivideCustomDto> divide;
    private double minRate;
    private double maxRate;
    private double minValue;
    private double maxValue;
}
