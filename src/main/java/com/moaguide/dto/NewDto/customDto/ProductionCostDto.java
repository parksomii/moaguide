package com.moaguide.dto.NewDto.customDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductionCostDto {
    private String cType; // 카테고리 타입
    private Integer year; // 월
    private Double productionCost; // 1등급 비율

    // 생성자
    public ProductionCostDto(Integer year, Double productionCost, String cType) {
        this.cType = cType;
        this.year = year;
        this.productionCost = productionCost;
    }
}
