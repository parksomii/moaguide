package com.moaguide.dto.NewDto.customDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ProductionCostDto {
    private Integer year; // 년
    private Double productionCost; // 1등급 비율

    // 생성자
    public ProductionCostDto(LocalDate year, Double productionCost) {
        this.year = year(year);
        this.productionCost = productionCost;
    }

    // 연도를 YYYY형식으로 변환하는 메서드
    private Integer year(LocalDate year) {
        return year.getYear();
    }
}
