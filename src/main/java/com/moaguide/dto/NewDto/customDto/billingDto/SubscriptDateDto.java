package com.moaguide.dto.NewDto.customDto.billingDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptDateDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate paymentDate;
}
