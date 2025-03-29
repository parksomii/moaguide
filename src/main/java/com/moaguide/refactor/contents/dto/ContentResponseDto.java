package com.moaguide.refactor.contents.dto;

import lombok.Getter;

@Getter
public class ContentResponseDto {

	private final ContentBaseDto base;
	private final Object object;

	// 하나의 생성자로 통합
	public ContentResponseDto(ContentBaseDto base, Object object) {
		this.base = base;
		this.object = object;
	}

	// object가 없을 때를 위한 생성자
	public ContentResponseDto(ContentBaseDto base) {
		this.base = base;
		this.object = null;
	}
}
