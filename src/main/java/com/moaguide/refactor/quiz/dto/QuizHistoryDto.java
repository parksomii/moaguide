package com.moaguide.refactor.quiz.dto;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class QuizHistoryDto {

	private int score;
	private String naver;
	private String insta;
	private LocalTime time;
}
