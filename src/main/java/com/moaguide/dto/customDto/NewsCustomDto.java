package com.moaguide.dto.customDto;

import com.moaguide.domain.news.News;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsCustomDto {
    // 메인 페이지의 최신 이슈를 보여주기 위한 DTO
    private Long id;
    private String title;
    private String category;
    private String link;
    private Date date;

    // NewsCustomDto에 News를 담기 위한 생성자
    public NewsCustomDto(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.date = news.getDate();
        this.link = news.getLink();
        this.category = news.getCategory();
    }
}
