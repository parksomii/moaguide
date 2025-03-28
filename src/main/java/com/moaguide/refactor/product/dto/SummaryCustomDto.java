package com.moaguide.refactor.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SummaryCustomDto {

	// 주요 상품 현황 및 상품 목록
	private String product_Id;
	private String name;
	private Long totalPrice;
	private Integer price;
	private double priceRate;
	private double lastDivide_rate;
	private String category;
	private String platform;
	private Boolean bookmark;
	private Boolean sale;
}
