package com.moaguide.dto.NewDto.customDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
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
