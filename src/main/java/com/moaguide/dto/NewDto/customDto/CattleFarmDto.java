package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CattleFarmDto {
    private String day;
    private Long  value;

    public CattleFarmDto(LocalDate date, Long  value) {
        this.day = startDate(date);
        this.value = value;
    }

    // 연도와 월을 YYYY.MM 형식으로 변환하는 메서드
    private String startDate(LocalDate date) {
        return String.format("%d.%02d", date.getYear(), date.getMonthValue());
    }
}
