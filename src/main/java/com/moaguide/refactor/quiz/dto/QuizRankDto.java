package com.moaguide.refactor.quiz.dto;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuizRankDto {

	private String name;
	private int score;
	private LocalTime time;
}
