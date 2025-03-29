package com.moaguide.refactor.notice.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDto {

	private Long id;
	private String title;
	private LocalDate date;
	private String content;
}
