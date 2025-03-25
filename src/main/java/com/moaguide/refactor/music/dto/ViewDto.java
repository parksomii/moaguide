package com.moaguide.refactor.music.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ViewDto {

	private String value;  // 조회수
	private String day;    // 조회날짜
}
