package com.moaguide.dto.NewDto.customDto;


import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@NoArgsConstructor
@Setter
public class BuildingReponseDto {
    private String productId;
    private String category;
    private String platform;
    private String name;
    private Integer price;
    private Double priceRate;
    private Long totalPrice;
    private Double dividend;
    private Double dividendRate;
    private Integer divideCycle;

    @ConstructorProperties({"productId", "category", "platform", "name", "price", "priceRate", "totalPrice", "dividend", "dividendRate", "divideCycle"})
    public BuildingReponseDto(String productId, String category, String platform, String name, Integer price, Double priceRate, Long totalPrice, Double dividend, Double dividendRate, Integer divideCycle) {
        this.productId = productId;
        this.category = category;
        this.platform = platform;
        this.name = name;
        this.price = price;
        this.priceRate = priceRate;
        this.totalPrice = totalPrice;
        this.dividend = dividend;
        this.dividendRate = dividendRate;
        this.divideCycle = divideCycle;
    }
}
