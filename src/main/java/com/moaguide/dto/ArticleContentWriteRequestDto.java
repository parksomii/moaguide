package com.moaguide.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleContentWriteRequestDto {

	private String title; // 아티클 제목

	private String authorName;  //아티클 작성자

	private String categoryName; // 카테고리 Name

	private String type; // 아티클 유형 (예: "아티클", "영상" "전체")

	@JsonProperty("isPremium")
	private boolean isPremium; // 프리미엄 여부

	private String imageLink; // 이미지 링크

	private String paywallUp; // Paywall 상단 내용

	private String paywallDown; // Paywall 하단 내용
}
