package com.moaguide.refactor.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleNonSubscriberDto {

	private Long articleId;
	private String categoryName;
	private String title;
	private String authorName;
	private String paywallUp;
	private String imgLink;
	private Integer views;
	private Integer likes;
	private String createdAt;
}
