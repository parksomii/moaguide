package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;

@Data
@NoArgsConstructor
public class finishCustomDto {
    private String productId;
    private String name;
    private String category;
    private String platform;
    private double sailRate;
    private long totalPrice;


    @ConstructorProperties({"productId", "name", "category", "platform", "totalPrice"})
    public finishCustomDto(String productId, String name, String category, String platform, long totalPrice) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.platform = platform;
        this.totalPrice = totalPrice;
    }

    @ConstructorProperties({"productId", "name", "category", "platform", "sailRate"})
    public finishCustomDto(String productId, String name, String category, String platform, double sailRate) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.platform = platform;
        this.sailRate = sailRate;
    }
}
