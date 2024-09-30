package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CattleTransactionDto {
    private LocalDate day;
    private Long  value;

    public CattleTransactionDto(LocalDate date, Long  value) {
        this.day = date;
        this.value = value;
    }

}
