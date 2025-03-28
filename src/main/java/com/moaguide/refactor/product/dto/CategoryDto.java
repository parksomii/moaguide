package com.moaguide.refactor.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryDto {

	private Long categoryId;
	private String name;
	private String description;

}
