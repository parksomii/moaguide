package com.moaguide.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RelatedContentDto {

  private Long articleId;
  private String title;       // 아티클 제목
  private String imgLink;     // 대표 이미지 URL
  private Timestamp createdAt; // 작성일
  private Integer views;      // 조회수
  private Integer likes;      // 좋아요 수
}
