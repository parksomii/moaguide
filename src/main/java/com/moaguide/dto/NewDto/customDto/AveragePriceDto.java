package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AveragePriceDto {
    private Integer month;
    private Double averagePrice;

    public AveragePriceDto(Integer month, Double averagePrice) {
        this.month = month;
        this.averagePrice = averagePrice;
    }
}
