package com.moaguide.refactor.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DetailTransactionResponseDto {

	private List<TransactionDto> transaction;
	private long max;
	private long min;

}
