package com.moaguide.refactor.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentDto {

	private String orderId;
	private String billingKey;
	private String customerKey;
	private Integer amount;
	private String nickname;
	private int failCount;
}
