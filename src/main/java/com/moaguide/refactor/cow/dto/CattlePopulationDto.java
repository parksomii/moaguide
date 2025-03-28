package com.moaguide.refactor.cow.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CattlePopulationDto {

	private LocalDate day;
	private Long value;

	public CattlePopulationDto(LocalDate date, Long value) {
		this.day = date;
		this.value = value;
	}


}
