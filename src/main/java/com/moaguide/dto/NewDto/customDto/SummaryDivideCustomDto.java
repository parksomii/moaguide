package com.moaguide.dto.NewDto.customDto;

import com.moaguide.domain.divide.Divide;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SummaryDivideCustomDto {
    private String product_Id;
    private String category;
    private String name;
    private double recruitmentRate;

}
