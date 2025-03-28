package com.moaguide.refactor.cow.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HanwooDetailDto {

	private String productId;
	private String category;
	private String platform;
	private String title;
	private String name;
	private Long recruitmentPrice;
	private Double recruitmentRate;
	private Long totalPrice;
	private LocalDate recruitmentDate;
	private int minInvestment;
	private String link;
	private Boolean bookmark;
}
