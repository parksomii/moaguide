package com.moaguide.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleQueryDto {

	private Long articleId;
	private String title;
	private String type;
	private Boolean isPremium;
	private Integer views;
	private Timestamp date;
	private Integer likes;
	private String description;
	private String img_link;
}
