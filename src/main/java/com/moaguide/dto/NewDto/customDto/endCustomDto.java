package com.moaguide.dto.NewDto.customDto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;

@Data
@NoArgsConstructor
public class endCustomDto {
    private String productId;
    private String name;
    private String category;
    private String platform;
    private long totalPrice;
    private Boolean bookmark;


    @ConstructorProperties({"productId", "name", "category", "platform", "totalPrice","bookmark"})
    public endCustomDto(String productId, String name, String category, String platform, long totalPrice, Boolean bookmark) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.platform = platform;
        this.totalPrice = totalPrice;
        this.bookmark = bookmark;
    }
}
