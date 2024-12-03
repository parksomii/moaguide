package com.moaguide.dto.NewDto.customDto.Coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class CouponAdminDto {
    private String name; // 쿠폰 이름
    private String couponCode; // 쿠폰 고유 번호
    private LocalDate createdAt; // 발행일
    private Integer months; // 쿠폰 적용 기간 (개월수)
    private String nickname; // User와 외래 키로 연결
    private Long couponId; //쿠폰 Id
    private Boolean redeemed; //사용 여부
    private LocalDate redeemedAt; //사용 날짜
}
