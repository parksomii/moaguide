package com.moaguide.refactor.product.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BookmarkProductDto {

	private String product_Id;
	private String name;
	private String category;
	private String platform;
	private Boolean sale;
}
