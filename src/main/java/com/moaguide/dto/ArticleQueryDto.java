package com.moaguide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

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
    private String paywallUp;
    private String img_link;
}
