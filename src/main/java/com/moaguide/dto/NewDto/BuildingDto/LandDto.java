package com.moaguide.dto.NewDto.BuildingDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LandDto {
    private int value;
    private String day;

    public LandDto(int value, String day) {
        this.value = value;
        this.day = day;
    }
}
