package com.moaguide.dto.NewDto.customDto;

import com.moaguide.refactor.news.dto.NewsCustomDto;
import java.util.List;
import lombok.Getter;

@Getter
public class DetailNewsResponseDto {

	private final List<NewsCustomDto> news;
	private final int total;
	private final int page;
	private final int size;

	public DetailNewsResponseDto(List<NewsCustomDto> news, int page, int size, int total) {
		this.news = news;
		this.page = page;
		this.size = size;
		this.total = total;
	}
}
