package com.moaguide.refactor.music.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SteamingDto {

	private Integer value;   // 스트리밍수
	private String day;     // 스트리밍날짜
}
