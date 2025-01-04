package com.moaguide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleOverviewDto {

	private Long articleId;
	private String title;
	private String type;
	private boolean isPremium;
	private String description;
	private String imgLink;
	private String categoryName;

}

