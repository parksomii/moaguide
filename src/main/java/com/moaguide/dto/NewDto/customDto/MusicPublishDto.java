package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MusicPublishDto {
    private String name;    // 상품명
    private String type;    // 상품유형
    private String singer;  // 가수
    private int piece;      // 발행수량
    private int basePrice;  // 발행가액
    private long totalPrice;    // 총 모집액
    private LocalDate issuDay;    // 상장일
}
