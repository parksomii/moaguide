package com.moaguide.dto.NewDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArtDetailDto {
    private String productId;
    private String category;
    private String platform;
    private String name;
    private String authorName;
    private Long recruitmentPrice;
    private Double recruitmentRate;
    private Long totalPrice;
    private String recruitmentDate;
    private int minInvestment;
}
