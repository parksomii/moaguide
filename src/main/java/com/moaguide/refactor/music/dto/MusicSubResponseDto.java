package com.moaguide.refactor.music.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
// 상세정보
public class MusicSubResponseDto {

	private String youtubeUrl;  // 유튜브 url
	private String youtubeTitle;  // 유튜브 제목
}
