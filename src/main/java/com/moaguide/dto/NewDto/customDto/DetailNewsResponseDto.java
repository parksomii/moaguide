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

    public DetailNewsResponseDto(List<NewsCustomDto> news,int page,int size,int total) {
        this.news = news;
        this.page = page;
        this.size = size;
        this.total = total;
    }
}
