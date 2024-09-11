package com.moaguide.dto.NewDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HanwooDetailDto {
    private String productId;
    private String category;
    private String platform;
    private String title;
    private String name;
    private Long recruitmentPrice;
    private Double recruitmentRate;
    private Long totalPrice;
    private LocalDate recruitmentDate;
    private int minInvestment;
}
