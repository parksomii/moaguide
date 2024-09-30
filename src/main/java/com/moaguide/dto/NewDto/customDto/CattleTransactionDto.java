package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CattleTransactionDto {
    private Date day;
    private Long  value;

    public CattleTransactionDto(Date date, Long  value) {
        this.day = date;
        this.value = value;
    }

}
