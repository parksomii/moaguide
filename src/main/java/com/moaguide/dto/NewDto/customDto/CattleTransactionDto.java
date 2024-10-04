package com.moaguide.dto.NewDto.customDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CattleTransactionDto {
    private LocalDate day;
    private Long  value;

    public CattleTransactionDto(int year, int month, Long value) {
        // 해당 년, 월의 1일을 LocalDate로 생성
        this.day = LocalDate.of(year, month, 1);
        this.value = value;
    }
}
