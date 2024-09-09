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
    private Long price;
    private Double priceRate;
    private String totalPrice;
    private Double dividend;
    private Double dividendRate;
    private Integer divideCycle;
}
