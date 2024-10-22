package com.moaguide.dto.NewDto.customDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SummaryIssupriceCustomDto {
    private String product_Id;
    private String category;
    private String name;
    private double recruitmentRate;
    private Boolean sale;

    public SummaryIssupriceCustomDto(String product_Id, String category, String name, double recruitmentRate, Boolean sale) {
        this.product_Id = product_Id;
        this.category = category;
        this.name = name;
        this.recruitmentRate = Math.round(recruitmentRate * 100.0) / 100.0; // 소수점 두 자리 반올림
        this.sale = sale;
    }
}
