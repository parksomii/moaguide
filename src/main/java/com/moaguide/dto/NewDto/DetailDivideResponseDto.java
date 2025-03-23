package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.customDto.DivideCustomDto;
import lombok.Getter;

import java.util.List;

@Getter
public class DetailDivideResponseDto {
    private final List<DivideCustomDto> divide;

    public DetailDivideResponseDto(List<DivideCustomDto> divideDtos) {
        this.divide = divideDtos;
    }
}
