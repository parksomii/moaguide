package com.moaguide.dto.NewDto.customDto;

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
    private Boolean bookmark;


    @ConstructorProperties({"productId", "name", "category", "platform", "sailRate","bookmark"})
    public finishCustomDto(String productId, String name, String category, String platform, double sailRate, Boolean bookmark) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.platform = platform;
        this.sailRate = sailRate;
        this.bookmark = bookmark;
    }
}
