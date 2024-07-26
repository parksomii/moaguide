package com.moaguide.dto.NewDto.customDto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class DetailNewsResponseDto {
    private List<NewsCustomDto> news;
    private int total;
    private int page;
    private int size;

    public DetailNewsResponseDto(Page<NewsCustomDto> news) {
        this.news = news.getContent();
        this.page = news.getNumber() + 1;
        this.size = news.getSize();
        this.total = news.getTotalPages();
    }
}
