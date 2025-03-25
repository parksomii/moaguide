package com.moaguide.refactor.music.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MusicSongDto {

	private String introduceSong;   // 곡 소개
	private String genre;           // 장르
	private String singer;          // 가수
	private String writer;          // 작사
	private String composing;           // 작곡
	private LocalDate announcementDate;    // 공표일자
}
