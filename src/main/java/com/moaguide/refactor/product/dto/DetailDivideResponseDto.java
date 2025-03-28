package com.moaguide.refactor.product.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class DetailDivideResponseDto {

	private final List<DivideCustomDto> divide;

	public DetailDivideResponseDto(List<DivideCustomDto> divideDtos) {
		this.divide = divideDtos;
	}
}
