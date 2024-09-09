package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.customDto.AveragePriceDto;
import com.moaguide.dto.NewDto.customDto.Grade1RateDto;
import com.moaguide.dto.NewDto.customDto.ProductionCostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HanwooPriceResponseDto {
    // 한우가격
    private List<AveragePriceDto> averagePrice;   // 평균가격
    private List<Grade1RateDto> grade1Rate;   // 1등급 출현율
    private List<ProductionCostDto> productionCost;   // 두당 생산비
}
