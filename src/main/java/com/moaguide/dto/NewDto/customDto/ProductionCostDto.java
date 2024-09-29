package com.moaguide.dto.NewDto.customDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ProductionCostDto {
    private String startDate; // 년
    private Double productionCost; // 1등급 비율

    // 생성자
    public ProductionCostDto(LocalDate preDe, Double productionCost) {
        this.startDate = startDate(preDe);
        this.productionCost = productionCost;
    }

    // 연도를 YYYY형식으로 변환하는 메서드
    private String startDate(LocalDate preDe) {
        return String.format("%d", preDe.getYear());
    }
}
