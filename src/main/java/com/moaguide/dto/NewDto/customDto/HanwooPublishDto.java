package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HanwooPublishDto {
    // 발행정보
    private String name;            // 작품명
    private String publisher;       // 증권종류
    private int piece;              // 증권수
    private int basePrice;          // 1주당 발행액
    private String totalPrice;      // 총 모집액
    private String recruitingType;  // 모집방법
    private String rightsStructure; // 권리구조
    private String revenueStructure; // 수익구조
    private String paymentDate;     // 납입기일
    private String subscription;    // 청약공고일
    private String allocationDate;  // 배정공고일
    private String criteriaDate;    // 배정기준일
}
