package com.moaguide.refactor.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleSubscriberDto {

	private Long articleId;
	private String categoryName;
	private String title;
	private String authorName;
	private String text; // PatwallUp + PaywallDown 합친 텍스트
	private String imgLink;
	private Integer views;
	private Integer likes;
	private String createdAt;
}
