package com.moaguide.dto;

import com.moaguide.domain.platform.Platform;
import com.moaguide.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SummaryDto {
    private String productId;
    private Platform platformId;
    private String name;
    private Integer piece;
    private Integer recruitmentRate;
    private Integer nowPiece;

    String getCategory(){
        return platformId.getCategory();
    };
    String getPlatform(){
        return platformId.getPlatform();
    };

    public Product toEntity(){
        return new Product(productId, platformId, name, piece, recruitmentRate,nowPiece);
    }
}
