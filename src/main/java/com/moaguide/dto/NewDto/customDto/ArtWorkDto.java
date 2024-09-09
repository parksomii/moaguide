package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArtWorkDto {
    // 작품 정보
    private String name;    // 작품명
    private String size;    // 작품 크기(가로x세로)
    private String productionDate;    // 제작년도
    private String material;    // 재료
    // 경매명
    // 출품처
    // 증권종류
    // 발행인
    // 추정가
}
