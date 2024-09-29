package com.moaguide.dto.NewDto.customDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class Grade1RateDto {
//    private String cType; // 카테고리 타입
    private String day;; // 연도 + 월
    private Double value; // 1등급 비율

    public Grade1RateDto(LocalDate preDe, Double grade1Rate) {
        this.day = startDate(preDe);
        this.value = grade1Rate;
    }
    /*public Grade1RateDto(LocalDate preDe, Double grade1Rate, String cType) {
        this.startDate = startDate(preDe);
        this.grade1Rate = grade1Rate;
        this.cType = cType;
    }*/

    // 연도와 월을 YYYY.MM 형식으로 변환하는 메서드
    private String startDate(LocalDate preDe) {
        return String.format("%d.%02d", preDe.getYear(), preDe.getMonthValue());
    }
}
