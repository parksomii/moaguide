package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArtPublishDto {
    // 발행정보
    private String name;    // 작품명
    private String publisher; // 증권종류
    private String authorName;    // 작가 이름
    private int piece; // 증권수
    private int basePrice; // 1주당 발행액
    private String totalPrice;    // 총 모집액
    private String subscriptionDate;    // 청약기간 YYYY.MM.DD ~ YYYY.MM.DD
}
