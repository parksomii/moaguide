package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MusicDivideResponseDto {
    private Double lastDividend;   // 최근 1주당 저작권료 - cd.dividend
    private Double lastDividendRate;   // 최근 저작권료 수익률 - cd.dividend_Rate
    private MusicDivideDto divideDto;    // 저작권료 상세정보
    private LocalDate paymentDay;      // 저작권료 지급일 - cd.Payment_day
    private int divideCycle;     // 저작권료 주기 - cd.divide_cycle
}
