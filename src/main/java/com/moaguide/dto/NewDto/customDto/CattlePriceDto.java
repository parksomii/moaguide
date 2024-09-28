package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CattlePriceDto {
    private String date;
    private Long value;

    public CattlePriceDto(LocalDate date, Long value) {
        this.date = startDate(date);
        this.value = value;
    }

    // 날짜를 yyyy-MM-dd 형식으로 변환
    private String startDate(LocalDate date) {
        return date.toString();
    }
}
