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
    private String material;    // 재료
    private String productionDate;    // 제작년도
    // 경매명
    // 출품처
    // 발행인
    // 추정가

/*    public ArtWorkDto(String name, String size, String productionDate, String material) {
        this.name = name;
        this.size = size;
        this.productionDate = productionDate;
        this.material = material;
    }*/
}
