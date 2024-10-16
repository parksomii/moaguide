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

    public SummaryIssupriceCustomDto(String product_Id, String category, String name, double recruitmentRate) {
        this.product_Id = product_Id;
        this.category = category;
        this.name = name;
        this.recruitmentRate = Math.round((recruitmentRate * 100.0 * 100.0))/ 100.0;
    }
}
