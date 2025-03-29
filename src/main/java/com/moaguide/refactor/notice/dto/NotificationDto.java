package com.moaguide.refactor.notice.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

	private Long id;
	private String link;
	private String message;
	private LocalDate Date;
}
