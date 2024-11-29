package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArtPublishDto {
    // 발행정보
    private String name;    // 작품명
    private String type; // 증권종류
    private String authorName;    // 작가 이름
    private int piece; // 증권수
    private int basePrice; // 1주당 발행액
    private String totalPrice;    // 총 모집액
    private LocalDate issuanceDate;    // 청약기간 YYYY.MM.DD ~ YYYY.MM.DD
    private String userPrice;

    /*public ArtPublishDto(String name, String publisher, String authorName, int piece, int basePrice, String totalPrice, String subscriptionDate) {
        this.name = name;
        this.publisher = publisher;
        this.authorName = authorName;
        this.piece = piece;
        this.basePrice = basePrice;
        this.totalPrice = totalPrice;
        this.subscriptionDate = subscriptionDate;
    }*/
}
