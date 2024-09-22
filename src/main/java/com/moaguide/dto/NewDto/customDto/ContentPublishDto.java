package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class ContentPublishDto {
    private String name;    // 상품명
    private String genre;   // 장르
    private String type;    // 증권종류
    private Long minAmount;   // 최소모집목표금액
    private Long maxAmount;   // 최대모집목표금액
    private Integer piece;          // 최소 모집수량
    private Integer basePrice;      // 1주당 가격
    private Integer minInvestment;  // 최소 투자금액
    private Date issuanceDate; // 증권발행일
    private Date expirationDate;   // 증권만기일

    public ContentPublishDto(String name, String genre, String type, Long minAmount, Long maxAmount,
                             Integer piece, Integer basePrice, Integer minInvestment,
                             Date issuanceDate, Date expirationDate) {
        this.name = name;
        this.genre = genre;
        this.type = type;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.piece = piece;
        this.basePrice = basePrice;
        this.minInvestment = minInvestment;
        this.issuanceDate = issuanceDate;
        this.expirationDate = expirationDate;
    }
}
