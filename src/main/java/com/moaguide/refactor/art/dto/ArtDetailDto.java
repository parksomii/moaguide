package com.moaguide.refactor.art.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArtDetailDto {

	private String productId;
	private String category;
	private String platform;
	private String name;
	private String authorName;
	private Long recruitmentPrice;
	private Double recruitmentRate;
	private Long totalPrice;
	private String recruitmentDate;
	private int minInvestment;
	private String link;
	private Boolean bookmark;
}
