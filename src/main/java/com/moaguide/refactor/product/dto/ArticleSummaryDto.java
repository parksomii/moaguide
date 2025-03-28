package com.moaguide.refactor.product.dto;


import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleSummaryDto {

	private String title;

	private String description;

	private String imageLink;

	private Timestamp date;

	private String link;
}
