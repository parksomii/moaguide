package com.moaguide.refactor.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestionCheckResponseDto {

	private Long questionId;
	private Integer solution;
	private Integer score;
}
