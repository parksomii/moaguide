package com.moaguide.dto.NewDto.customDto.Coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class CouponUserDto {
    private final Long couponId;
    private final String CouponName;
    private final LocalDate createdAt;
}
