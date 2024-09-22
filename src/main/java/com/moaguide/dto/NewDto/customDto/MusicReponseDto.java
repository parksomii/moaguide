package com.moaguide.dto.NewDto.customDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@NoArgsConstructor
@Setter
public class MusicReponseDto {
    private String product_Id;
    private String category;
    private String platform;
    private String name;
    private String singer;
    private Integer price;
    private Double priceRate;
    private Long totalPrice;
    private Double lastDivide;
    private Double lastDivide_rate;
    private Integer divideCycle;

    @ConstructorProperties({"product_Id", "category", "platform", "name", "singer", "price", "priceRate", "totalPrice", "lastDivide", "lastDivide_rate", "divideCycle"})
    public MusicReponseDto(String product_Id, String category, String platform, String name, String singer, Integer price, Double priceRate, Long totalPrice, Double lastDivide, Double lastDivide_rate, Integer divideCycle) {
        this.product_Id = product_Id;
        this.category = category;
        this.platform = platform;
        this.name = name;
        this.singer = singer; // 가수 이름 추가
        this.price = price;
        this.priceRate = priceRate;
        this.totalPrice = totalPrice;
        this.lastDivide = lastDivide;
        this.lastDivide_rate = lastDivide_rate;
        this.divideCycle = divideCycle;
    }

}
