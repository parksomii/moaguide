package com.moaguide.dto.NewDto.customDto.billingDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BillingCouponUSer {

    private Long id;
    private Long CouponId;
    private String couponName;
    private String nickname;
    private int month;
}
