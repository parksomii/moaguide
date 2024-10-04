package com.moaguide.dto.NewDto.customDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CattleSaleDto {
    private LocalDate day;
    private Long  value;

    public CattleSaleDto(LocalDate date, Long  value) {
        this.day = date;
        this.value = value;
    }
}
