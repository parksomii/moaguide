package com.moaguide.refactor.building.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AreaDto {

	private int areaSize;

	private String productName;

	private String polygon;

	private double latitude;

	private double longitude;

	private String color;
}
