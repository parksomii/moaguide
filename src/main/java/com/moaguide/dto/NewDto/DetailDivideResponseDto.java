package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.customDto.DivideCustomDto;
import lombok.Getter;

import java.util.List;

@Getter
public class DetailDivideResponseDto {
    private List<DivideCustomDto> divide;
    private int divideCycle;

    public DetailDivideResponseDto(List<DivideCustomDto> divideDtos, Integer dividendCycle) {
        this.divide = divideDtos;
        this.divideCycle = dividendCycle;
    }
}
