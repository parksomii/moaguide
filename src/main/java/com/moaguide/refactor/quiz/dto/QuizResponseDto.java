package com.moaguide.refactor.quiz.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QuizResponseDto {

	private Long quizId;
	private String type;
	private String failList;
	private String fail_answer;
}
