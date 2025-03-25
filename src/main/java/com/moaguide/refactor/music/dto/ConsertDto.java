package com.moaguide.refactor.music.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConsertDto {

	private String title;
	private String place;
	private String period;
	private String imageUrl;
	private String link;
}
