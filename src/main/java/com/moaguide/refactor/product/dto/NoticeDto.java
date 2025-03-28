package com.moaguide.refactor.product.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class NoticeDto {

	private long id;
	private String title;
	private LocalDate noticeDay;
	private String content;

	public NoticeDto(long id, String title, LocalDate noticeDay) {
		this.id = id;
		this.title = title;
		this.noticeDay = noticeDay;
	}

	public NoticeDto(String title, LocalDate noticeDay, String content) {
		this.title = title;
		this.noticeDay = noticeDay;
		this.content = content;
	}
}
