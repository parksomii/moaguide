package com.moaguide.dto.NewDto.BuildingDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LandDto {
    private int landPrice;
    private String baseYear;
    private String baseDay;

    public LandDto(int landPrice, String baseYear, String baseDay) {
        this.landPrice = landPrice;
        this.baseYear = baseYear;
        this.baseDay = baseDay;
    }
}
