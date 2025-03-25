package com.moaguide.refactor.music.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
// 기본정보
public class MusicBaseResponseDto {

	// 발행정보
	private MusicPublishDto musicPublish;
	// 곡 정보
	private MusicSongDto musicSong;
	// 저작권료 정보
	private MusicDivideResponseDto musicDivide;
}
