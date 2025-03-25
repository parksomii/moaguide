package com.moaguide.refactor.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestionDto {

	private String explanation;
	private String choice1;
	private String choice2;
	private String choice3;
	private String choice4;
	private String choice5;
}
