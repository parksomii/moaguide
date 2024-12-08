package com.moaguide.dto.NewDto.customDto.billingDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptDateDto {
    private Date startDate;
    private Date endDate;
    private Date paymentDate;
}
