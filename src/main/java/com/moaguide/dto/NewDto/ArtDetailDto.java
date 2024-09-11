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
    private Integer recruitmentPrice;
    private Double recruitmentRate;
    private String totalPrice;
    private String subscriptionDate;
    private int minInvestment;
}
