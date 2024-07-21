package com.moaguide.dto;

import com.moaguide.domain.platform.Platform;
import com.moaguide.domain.summary.Summary;
import com.moaguide.domain.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


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

    public Summary toEntity(){
        return new Summary(productId, platformId, name, piece, recruitmentRate,nowPiece);
    }
}
