package com.moaguide.refactor.cow.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CattleTransactionDto {

	private LocalDate day;
	private Long value;

	public CattleTransactionDto(int year, int month, Long value) {
		// 해당 년, 월의 1일을 LocalDate로 생성
		this.day = LocalDate.of(year, month, 1);
		this.value = value;
	}
}
