package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CattleTransactionDto {
    private LocalDate day;
    private BigDecimal value;

    public CattleTransactionDto(LocalDate date, BigDecimal value) {
        this.day = date;
        this.value = value;
    }

}
