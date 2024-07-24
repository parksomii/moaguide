package com.moaguide.dto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SummaryCustomDto {
    // 주요 상품 현황 및 상품 목록
    private String product_Id;
    private String category;
    private String platfrom;
    private String name;
    private int price;
    private double priceRate;
    private String totalPrice;
    private double lastDivide_rate;
}
