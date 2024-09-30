package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.customDto.CattleFarmDto;
import com.moaguide.dto.NewDto.customDto.CattlePopulationDto;
import com.moaguide.dto.NewDto.customDto.CattleSaleDto;
import com.moaguide.dto.NewDto.customDto.CattleTransactionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class HanwooMarketResponseDto {
    private List<CattlePopulationDto> cattlePopulation; // 한우 사육두수
    private List<CattleSaleDto> cattleSale; // 한우 매각두수
    private List<CattleFarmDto> cattleFarm; // 한우사육농가수
    private List<CattleTransactionDto> cattleTransaction;   // 한우 거래정육량


    public HanwooMarketResponseDto(List<CattlePopulationDto> cattlePopulation,
                                   List<CattleSaleDto> cattleSale,
                                   List<CattleFarmDto> cattleFarm,
                                   List<CattleTransactionDto> cattleTransaction) {
        this.cattlePopulation = cattlePopulation != null ? cattlePopulation : new ArrayList<>();
        this.cattleSale = cattleSale != null ? cattleSale : new ArrayList<>();
        this.cattleFarm = cattleFarm != null ? cattleFarm : new ArrayList<>();
        this.cattleTransaction = cattleTransaction != null ? cattleTransaction : new ArrayList<>();
    }

}
