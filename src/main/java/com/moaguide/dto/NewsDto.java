package com.moaguide.dto;

import com.moaguide.domain.news.News;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto {
    private Long id;
    private String keyword;
    private String title;
    private String link;
    private Date date;
    private String description;
    private int views;

    public NewsDto(News news) {
        this.id = news.getId();
        this.keyword = news.getKeyword();
        this.title = news.getTitle();
        this.link = news.getLink();
        this.date = news.getDate();
        this.description = news.getDescription();
        this.views = news.getViews();
    }
}
