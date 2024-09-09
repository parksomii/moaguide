package com.moaguide.dto.NewDto.customDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class BuildingReponseDto {
    private String productId;
    private String category;
    private String platform;
    private String name;
    private long price;
    private double priceRate;
    private String totalPrice;
    private double dividend;
    private double dividendRate;
    private int divideCycle;
}
