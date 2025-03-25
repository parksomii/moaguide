package com.moaguide.refactor.building.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RentDto {

	private int year;
	private int quarter;
	private String region;
	private double value;
}
