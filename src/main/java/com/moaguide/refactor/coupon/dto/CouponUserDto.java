package com.moaguide.refactor.coupon.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CouponUserDto {

	private final Long couponId;
	private final String CouponName;
	private final LocalDate createdAt;
}
