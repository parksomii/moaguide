package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContentInvestmentDto {
    private Long totalBudget; // 총 예산규모
    private Integer unitPrice;   // 객단가
    private String profitRatio; // 손익배당비율
    private String breakEvenPoint;  // 추정 손익분기점
}
