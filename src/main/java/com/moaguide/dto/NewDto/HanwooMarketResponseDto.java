package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.customDto.CattleFarmDto;
import com.moaguide.dto.NewDto.customDto.CattlePopulationDto;
import com.moaguide.dto.NewDto.customDto.CattlePriceDto;
import com.moaguide.dto.NewDto.customDto.CattleSaleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HanwooMarketResponseDto {
    private List<CattlePopulationDto> cattlePopulation; // 한우 사육두수
    private List<CattleSaleDto> cattleSale; // 한우 매각두수
    private List<CattleFarmDto> cattleFarm; // 한우사육농가수
    private List<CattlePriceDto> cattlePrice;   // 한우 거래정육량
}
