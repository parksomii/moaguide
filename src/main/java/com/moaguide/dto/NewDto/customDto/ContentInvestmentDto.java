package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContentInvestmentDto {
    private String totalBudget; // 총 예산규모
    private String unitPrice;   // 객단가
    private String profitRatio; // 손익배당비율
    private String breakEvenPoint;  // 추정 손익분기점
    private String productionCost;  // 제작비
}
