package com.moaguide.refactor.product.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class DetailNoticeResponseDto {

	private final List<NoticeDto> notice;
	private final int total;
	private final int page;
	private final int size;

	public DetailNoticeResponseDto(Page<NoticeDto> noticeDtos) {
		this.notice = noticeDtos.getContent();
		this.page = noticeDtos.getNumber() + 1;
		this.size = noticeDtos.getSize();
		this.total = noticeDtos.getTotalPages();
	}


}
