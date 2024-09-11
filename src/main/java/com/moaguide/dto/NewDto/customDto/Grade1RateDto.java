package com.moaguide.dto.NewDto.customDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Grade1RateDto {
    private String cType; // 카테고리 타입
    private Integer month; // 월
    private Double grade1Rate; // 1등급 비율

    public Grade1RateDto(Integer month, Double grade1Rate, String cType) {
        this.month = month;
        this.grade1Rate = grade1Rate;
        this.cType = cType;
    }
}
