package com.moaguide.refactor.cow.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductionCostDto {

	private String day; // 년
	private Double value; // 1등급 비율

	// 생성자
	public ProductionCostDto(LocalDate preDe, Double productionCost) {
		this.day = startDate(preDe);
		this.value = productionCost;
	}

	// 연도를 YYYY형식으로 변환하는 메서드
	private String startDate(LocalDate preDe) {
		return String.format("%d", preDe.getYear());
	}
}
