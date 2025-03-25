package com.moaguide.refactor.building.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StayRateDto {

	private Date day;
	private Double rate;
	private Double value;
}

