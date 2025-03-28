package com.moaguide.refactor.product.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SummaryRecentDto {

	private List<SummaryIssupriceCustomDto> divide; // 최근 배당금 발표
	private List<SummaryCustomDto> summary; // 최근 상품
	private List<ArticleSummaryDto> article;
}
