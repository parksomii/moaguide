package com.moaguide.refactor.payments.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptDateDto {

	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDate paymentDate;
}
