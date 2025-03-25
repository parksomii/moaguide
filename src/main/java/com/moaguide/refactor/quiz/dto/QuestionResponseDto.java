package com.moaguide.refactor.quiz.dto;

import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestionResponseDto {

	private List<Integer> answer;
	private String insta;
	private String naver;
	private LocalTime time;
	private String type;
}
